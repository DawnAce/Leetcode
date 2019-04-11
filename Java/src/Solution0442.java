import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个整数数组 a，其中1 ≤ a[i] ≤ n （n为数组长度）, 其中有些元素出现两次而其他元素出现一次。
 *
 * 找到所有出现两次的元素。
 *
 * 你可以不用到任何额外空间并在O(n)时间复杂度内解决这个问题吗？
 */
public class Solution0442 {
    //这个方法其实可以想象成，有另外一个flag数组进行记录，记录哪些数已经出现过，那么第二次遍历到已经出现过的数，就是重复的数
    //现在将两个数组合为一个(原数组flag数组)，采用的策略是，使用负数表示已经出现过的数
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new LinkedList<Integer>();
        if(nums == null)
            return result;
        for(int i=0; i<nums.length; i++){
            int location = Math.abs(nums[i])-1;
            if(nums[location] < 0){
                result.add(Math.abs(nums[i]));
            }else
                nums[location] = -nums[location];
        }
        for(int i=0; i<nums.length; i++)
            nums[i] = Math.abs(nums[i]);

        return result;
    }

    public List<Integer> findDuplicates0(int[] nums) {
        for(int i=0; i<nums.length; ++i){
            if(nums[i]!=i+1 && nums[nums[i]-1]!=nums[i])
                swap(nums, i, nums[i--]-1);
        }
        List<Integer> result = new LinkedList<>();
        for(int i=0; i<nums.length; ++i)
            if(nums[i] != i+1) result.add(nums[i]);
        return result;
    }

    //不用额外空间交换两数
    private void swap(int[] nums, int index0, int index1){
        nums[index0] ^= nums[index1];
        nums[index1] ^= nums[index0];
        nums[index0] ^= nums[index1];
    }

    public static void main(String[] args) {
        int[] nums =
                {4,3,2,7,8,2,3,1};
        List<Integer> result = new Solution0442().findDuplicates(nums);
        System.out.println(result);
    }
}
