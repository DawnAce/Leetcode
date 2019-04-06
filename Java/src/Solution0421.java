import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个非空数组，数组中元素为 a0, a1, a2, … , an-1，其中 0 ≤ ai < 231 。
 *
 * 找到 ai 和aj 最大的异或 (XOR) 运算结果，其中0 ≤ i,  j < n 。
 *
 * 你能在O(n)的时间解决这个问题吗？
 */
public class Solution0421 {
    //使用前缀树进行寻找，每一位使用贪心算法
    class Trie{
        Trie[] children;
        int val;
        boolean isEnd;

        public Trie(){
            children = new Trie[2];
        }

        public void insert(int num, int level){
            Trie holder = this;
            while(level > 0){
                int index = (num&level)==0 ? 0 : 1;
                if(holder.children[index] == null)
                    holder.children[index] = new Trie();
                holder = holder.children[index];
                level >>>= 1;
            }
            holder.val = num;
            holder.isEnd = true;
        }
    }

    //先找出最左边的1所代表的数字，这样构建树的时候可以省掉很多空间
    public int findMaximumXOR(int[] nums){
        int max = 0;
        for(int num : nums)
            max = Math.max(max, Integer.highestOneBit(num));
        Trie root = new Trie();
        for(int num : nums)
            root.insert(num, max);
        return helper(root.children[0], root.children[1]);
    }

    private int helper(Trie zero, Trie one){
        if(zero==null || one==null)
            return zero==null ? helper(one.children[0],one.children[1])
                    : helper(zero.children[0], zero.children[1]);
        if(zero.isEnd && one.isEnd)
            return zero.val ^ one.val;
        if(zero.children[0] == null){
            return helper(zero.children[1], one.children[0]==null ? one.children[1] : one.children[0]);
        }else if(zero.children[1] == null){
            return helper(zero.children[0], one.children[1]==null ? one.children[0] : one.children[1]);
        }else if(one.children[0] == null){
            return helper(zero.children[0]==null ? zero.children[1] : zero.children[0], one.children[1]);
        }else if(one.children[1] == null){
            return helper(zero.children[1]==null ? zero.children[0] : zero.children[1], one.children[0]);
        }else
            return Math.max(helper(zero.children[0], one.children[1]), helper(zero.children[1], one.children[0]));
    }

    //核心思路是，每次提供一个目前可能达到的最大值候选项
    //然后收集[hightest, index]之间有多少种可能值，查看在这些值中，能否组成最大值候选项
    //时间复杂度为O(n*64)=O(n),因为最外层for循环最多只有32次，两个内部for循环都是最多遍历n次
    public int findMaximumXOR0(int[] nums){
        int max = 0, mask = 0;  //mask为[hightest,index]之间全是1的数，用来获取数组中不同的组合
        for(int num : nums)
            mask = Math.max(mask, Integer.highestOneBit(num));

        for(int i=mask; i>0; i>>=1){
            mask = mask | i;    //补充1，用来下面获取[hightest,index]之间不同的组合
            Set<Integer> set = new HashSet<>();
            for(int num : nums)
                set.add(num & mask);
            //tmp代表目前潜在的最大值。目前潜在的最大值肯定为上一个最大值上加上本位的1
            int tmp = max | i;
            for(int prefix : set)
                //其实就是set中有两个num使得num0^num1 = tmp
                if(set.contains(tmp ^ prefix)){
                    max = tmp;
                    break;
                }
        }
        return max;
    }

    public static void main(String[] args) {
        int[] nums =
                {5};
        int result = new Solution0421().findMaximumXOR(nums);
        System.out.println(result);
    }
}
