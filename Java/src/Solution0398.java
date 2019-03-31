import java.util.*;

/**
 * 给定一个可能含有重复元素的整数数组，要求随机输出给定的数字的索引。 您可以假设给定的数字一定存在于数组中。
 *
 * 注意：
 * 数组大小可能非常大。 使用太多额外空间的解决方案将不会通过测试。
 */
public class Solution0398 {
    //这个方法利用了最基本的，不知长度数组，获取随机数的方法，空间上虽然复杂度很低，但是时间上为O(n)
    int[] nums;
    Random r;

    public Solution0398(int[] nums) {
        this.nums = nums;
        r = new Random();
    }

    public int pick(int target) {
        int result = 0, count = 0;
        for(int i=0; i<nums.length; ++i){
            //随机的数，只要保证每次都有可能出现就行了，这样就只有两个选择0和len-1
            if(nums[i]==target && r.nextInt(++count)==0)
                result = i;
        }
        return result;
    }
    //这个方法快是很快，但是空间复杂度太高，因为需要新建O(n)空间用于存储相关信息
    //虽然用数组holder表面上也是O(n)的空间复杂度，但是实际上内存中指向同一块
//    Map<Integer, List<Integer>> map;
//
//    public Solution0398(int[] nums) {
//        map = new HashMap<>();
//        for(int i=0; i<nums.length; ++i)
//            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
//    }
//
//    public int pick(int target) {
//        List<Integer> index = map.get(target);
//        return index.get(new Random().nextInt(index.size()));
//    }
}
