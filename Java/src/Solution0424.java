/**
 * 给你一个仅由大写英文字母组成的字符串，你可以将任意位置上的字符替换成另外的字符，总共可最多替换 k 次。
 * 在执行上述操作后，找到包含重复字母的最长子串的长度。
 *
 * 注意:
 * 字符串长度 和 k 不会超过 104。
 */
public class Solution0424 {
    /**
     * There's no edge case for this question. The initial step is to extend the window to its limit,
     * that is, the longest we can get to with maximum number of modifications.
     * Until then the variable start will remain at 0.
     *
     * Then as end increase, the whole substring from 0 to end will violate the rule,
     * so we need to update start accordingly (slide the window).
     * We move start to the right until the whole string satisfy the constraint again.
     * Then each time we reach such situation, we update our max length.
     * @param s
     * @param k
     * @return
     */
    public int characterReplacement(String s, int k) {
        int len = s.length();
        int[] count = new int[26];
        int start = 0, maxCount = 0, maxLength = 0;
        for (int end = 0; end < len; end++) {
            //这里其实使用了贪心思想，在window长度一定的情况下，一定是选择在window中字符最多的为基准
            maxCount = Math.max(maxCount, ++count[s.charAt(end) - 'A']);
            //以基准字符为变量，如果字符太少，导致k将其他的字符变为基准字符都不够用，那么需要移动窗口左端
            while (end - start + 1 - maxCount > k) {
                count[s.charAt(start) - 'A']--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }

    //使用Set记录哪些字符出现过，避免不必要的计算
    public int characterReplacement0(String s, int k){
        int max = 0, charSet = 0;
        for(char c : s.toCharArray())
            charSet |= 1<<(c-'A');
        for(int index=0; index<26; ++index){
            if((charSet&(1<<index)) == 0) continue;
            char c = (char)(index+'A');
            int left = 0, count = k;
            for(int i=0; i<s.length(); ++i){
                if(s.charAt(i) !=c ){
                    if(count > 0) --count;
                    else {
                        //找到第一个不是目标字符的
                        while (left < i &&
                                s.charAt(left) == c) ++left;
                        ++left;
                    }
                }
                max = Math.max(max, i-left+1);
            }
        }
        return max;
    }

    //按照A-Z的顺序遍历26次，每一次遍历时使用窗口思想
    public int characterReplacement1(String s, int k) {
        int max = 0;
        for(char c='A'; c<='Z'; ++c) {
            int left = 0, count = k;
            for(int i=0; i<s.length(); ++i){
                if(s.charAt(i) !=c ){
                    if(count > 0) --count;
                    else {
                        //找到第一个不是目标字符的
                        while (left < i &&
                                s.charAt(left) == c) ++left;
                        ++left;
                    }
                }
                max = Math.max(max, i-left+1);
            }
        }
        return max;
    }

    public static void main(String[] args) {
        String s =
                "ABBB";
        int k = 2;
        int result = new Solution0424().characterReplacement(s, k);
        System.out.println(result);
    }
}
