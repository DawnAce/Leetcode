import java.util.Arrays;

/**
 * 给定一个只包含正整数的非空数组。是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。
 *
 * 注意:
 *
 * 每个数组中的元素不会超过 100
 * 数组的大小不会超过 200
 */
public class Solution0416 {
    //这是答案中最快的方法，核心思想跟我的一样，只是在细节的处理上比我好太多
    //精髓在于相同元素的跳过判断，其实如果加上排序可能效果会更好
    public boolean canPartition(int[] nums) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 == 1) return false;
        return dfs ( nums, 0, sum / 2);
    }

    public boolean dfs(int[] nums, int index, int target) {
        if (target == 0) return true;
        if (index >= nums.length || target < 0) return false;
        if (dfs(nums, index + 1, target - nums[index])) return true;
        int j = index + 1;
        //这一块判断真实太精妙了！
        while (j < nums.length && nums[index] == nums[j]) {
            j++;
        }
        return dfs(nums, j, target);
    }

    //核心思路是利用DFS，因为每一位的数有两个状态，选或者不选，这是典型的可以利用DFS解决的问题
    //但是需要注意的一点是如果不进行特殊处理可能会超时
    //一开始我没有使用sort对数组排序，后来超时了，之后先排序判断最大的数是否过大导致没有需要再进行判断
    //还有一个问题需要注意，最好先判断剩下的值是否大于当前值，这样可以减少一次递归
    public boolean canPartition0(int[] nums) {
        int sum = 0;
        Arrays.sort(nums);
        for(int num : nums) sum += num;
        if((sum&1)==1 || nums[nums.length-1]>sum/2) return false;
        return helper(nums, sum>>1, nums.length-1);
    }

    private boolean helper(int[] nums, int total, int index){
        if(total == 0) return true;
        if(total<0 || index<0) return false;
        if(total >= nums[index]){
            boolean contain = helper(nums, total-nums[index], index-1);
            if(contain) return true;
        }
        return helper(nums, total, index-1);
    }
}
