import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * 二进制手表顶部有 4 个 LED 代表小时（0-11），底部的 6 个 LED 代表分钟（0-59）。
 *
 * 每个 LED 代表一个 0 或 1，最低位在右侧。
 * 给定一个非负整数 n 代表当前 LED 亮着的数量，返回所有可能的时间。
 *
 * 注意事项:
 *
 * 输出的顺序没有要求。
 * 小时不会以零开头，比如 “01:00” 是不允许的，应为 “1:00”。
 * 分钟必须由两位数组成，可能会以零开头，比如 “10:2” 是无效的，应为 “10:02”。
 */
public class Solution0401 {
    private static final int[] HOURS = {8, 4, 2, 1};
    private static final int[] MINS = {32, 16, 8, 4, 2, 1};
    private static final int MAX_HOUR = 11;
    private static final int MAX_MIN = 59;
    // 这是答案中运行最快的方法，且代码非常简洁，主要原因在于它将小时和分钟分开计算
    // 而我是将小时和分钟都看作整数，1的个数看作变量，这样可能会产生很多不必要的计算过程
    public List<String> readBinaryWatch(int n) {
        if (n == 0)
            return Arrays.asList("0:00");
        List<String> res = new ArrayList<>();
        helper(res, n, 0, 0, 0, 0);
        return res;
    }
    private void helper(List<String> res, int n, int hour, int min, int startHour, int startMin) {
        if (hour > MAX_HOUR || min > MAX_MIN)
            return;

        if (n == 0) {
            StringBuilder ans = new StringBuilder(hour + ":");
            if (min < 10)
                ans.append('0');
            ans.append(min);
            res.add(ans.toString());
            return;
        }
        for (int i = startHour; i < HOURS.length; i++)
            helper(res, n-1, hour + HOURS[i], min, i+1, startMin);
        for (int i = startMin; i < MINS.length; i++)
            helper(res, n-1, hour, min + MINS[i], HOURS.length, i+1);
    }

    //这是Stefan Pochmann的方法，核心思想是，将小时和分钟进行Hash编码(hour占据高4位，minute占据低6位)
    //然后计算数字其中1的个数是否等于预想值，真是TM太聪明了！！！
    public List<String> readBinaryWatch0(int num) {
        List<String> times = new ArrayList<>();
        for (int h=0; h<12; h++)
            for (int m=0; m<60; m++)
                if (Integer.bitCount(h * 64 + m) == num)
                    times.add(String.format("%d:%02d", h, m));
        return times;
    }

    //核心思想是DFS加Memory，记录下来n个1的数字可能性
    public List<String> readBinaryWatch1(int num) {
        List<String> result = new LinkedList<>();
        if(num > 8) return result;
        List<Integer>[] map = new List[7];

        int[] nums = new int[6];
        for(int i=0; i<=num; ++i){
            if(i > 4) break;
            else if(num - i > 6) continue;
            //延时计算，相比于初始化的时候计算6种情况，节省了计算资源
            if(map[i] == null) helper(nums, 0, i,i, map);
            if(map[num-i] == null) helper(nums, 0, num-i, num-i, map);
            for(int hour : map[i]){
                if(hour > 11) continue;
                for(int minute : map[num-i]){
                    String temp = hour + ":" + (minute<10 ? "0"+minute : minute);
                    result.add(temp);
                }
            }
        }
        return result;
    }

    private void helper(int[] nums, int index, int total,
                        int remain, List<Integer>[] map){
        if(remain == 0){
            int num = 0;
            for(int i=nums.length-1; i>=0; --i)
                num = num*2 + nums[i];
            //这个地方得注意，因为是在递归函数中初始化，所以不管值是否符合都需要初始化
            //因为上面的循环默认不存在的列表长度为0
            if(map[total] == null)
                map[total] = new LinkedList<>();
            if(num < 60) map[total].add(num);
            return;
        }
        if(remain>nums.length-index || index==nums.length) return;
        nums[index] = 1;
        helper(nums, index+1, total, remain-1, map);

        nums[index] = 0;
        helper(nums, index+1, total, remain, map);
    }

    public static void main(String[] args) {
        int n = 6;
        List<String> result = new Solution0401().readBinaryWatch0(n);
        System.out.println(result);
    }
}
