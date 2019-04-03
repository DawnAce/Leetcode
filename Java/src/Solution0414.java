/**
 * 给定一个非空数组，返回此数组中第三大的数。如果不存在，则返回数组中最大的数。要求算法时间复杂度必须是O(n)。
 * ps:[2,2,3,1]返回1，因为2相同，所以只算一次
 */
public class Solution0414 {
    //这段代码很精简，且逻辑很清楚，这里需要注意使用了Long来判别是否存在第三大的数
    public int thirdMax(int[] nums){
        long firstMax = Long.MIN_VALUE, secondMax = Long.MIN_VALUE, thirdMax = Long.MIN_VALUE;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] == firstMax || nums[i] == secondMax || nums[i] == thirdMax)
                continue;
            if(nums[i] > firstMax){
                thirdMax = secondMax;
                secondMax = firstMax;
                firstMax = nums[i];
            } else if(nums[i] > secondMax){
                thirdMax = secondMax;
                secondMax = nums[i];
            } else if(nums[i] > thirdMax){
                thirdMax = nums[i];
            }
        }
        return (int)(thirdMax == Long.MIN_VALUE ? firstMax : thirdMax);
    }

    //我的代码太复杂了！！！
    public int thirdMax0(int[] nums) {
        Integer[] record = new Integer[3];
        for(int i=0; i<nums.length; ++i){
            for(int j=0; j<record.length; ++j){
                if(record[j] == null){
                    record[j] = nums[i];
                    break;
                }else if(nums[i] > record[j]){
                    for(int k=2; k>j; --k)
                        record[k] = record[k-1];
                    record[j] = nums[i];
                    break;
                }else if(nums[i] == record[j])
                    break;
            }
        }
        return record[2]==null ? record[0] : record[2];
    }
}
