import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 找到给定字符串（由小写字符组成）中的最长子串 T ， 要求 T 中的每一字符出现次数都不少于 k 。输出 T 的长度。
 */
public class Solution0395 {
    //一开始想的是，遍历一遍数组，记录下来满足大于K的字符集合{x,...,y}和不满足条件的字符集合{i,...,j}
    //找到x的最小index0和y的最大index1
    //然后采用窗口的思想进行计算(初始化窗口为[index0,index1]，先计算其中各字符的数量，碰到{i,...,j}中的字符，则重新移动窗口，但是问题来了我应该移动左窗口还是右窗口?)
    //后来看了论坛上的一些答案，发现起始我每一次只需要知道我需要计算的字符范围，然后知道哪些字符不符合条件，以不符合条件的字符为分隔符，切成更小的范围就可以了
    //因为题目假设都为小写字符，那么每一次不符合要求的字符种类至少为1种，那么每递归一次，我就能减少至少一种字符，所以最多递归25次
    //这样时间复杂度最多为26n(最后一次递归不知道是只有一种字符，所以还需要遍历)
    //论坛上有很多方法，都是每次递归以不符合要求的数量最少的字符为分隔符号，这样其实很慢，我可以以任意不符合条件的字符为分隔符
    //那么一次递归可能去掉多个字符，加速了计算，空间上只增加了常数空间(boolean数组而以，固定长度为26)
    public int longestSubstring(String s, int k) {
        if(s == null) return 0;
        return helper(s.toCharArray(), 0, s.length(), k);
    }

    private int helper(char[] array, int start, int end, int k){
        if(end - start < k) return 0;   //数量不够肯定不满足
        int[] counts = new int[26];
        for(int i=start; i<end; ++i)
            ++counts[array[i]-'a'];
        boolean[] inValid = new boolean[26];
        boolean allValid = true;
        //记录不符合要求的字符有哪些，同时统计是否有字符不符合要求
        for(int i=0; i<counts.length; ++i) {
            if (counts[i] >= k) inValid[i] = true;
            else if(counts[i]  != 0) allValid = false;
        }
        //如果都符合要求，那么长度显然为本次递归区间的长度
        if(allValid) return end - start;
        int pre = start, max = 0;
        for(int i=start; i<end; ++i){
            if(!inValid[array[i]-'a']){
                max = Math.max(max, helper(array, pre, i, k));
                pre = i+1;
            }
        }
        max = Math.max(max, helper(array, pre, end, k));
        return max;
    }
}
