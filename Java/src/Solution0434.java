/**
 * 统计字符串中的单词个数，这里的单词指的是连续的不是空格的字符。
 *
 * 请注意，你可以假定字符串里不包括任何不可打印的字符。
 */
public class Solution0434 {
    //核心思路跟419计算战舰类似，只需要计算单词的开头即可(startWord为false,当前字符不为空格)
    public int countSegments(String s){
        int count = 0;
        boolean startWord = false;
        for(char c : s.toCharArray()){
            if(!startWord && c!=' '){
                startWord = true;
                ++count;
            }else if(c == ' ')
                startWord = false;
        }
        return count;
    }

    //利用系统的函数，时间复杂度为O(4*n)
    public int countSegments0(String s) {
        if(s==null || s.trim().length()==0) return 0;
        return s.trim().split("\\s+").length;
    }

    //这是代码中同样利用系统内置函数，但是代码更简洁的解法！！！
    public int countSegments1(String s) {
        return ("x " + s).split(" +").length - 1;
    }

    public static void main(String[] args) {
        String s = "a   ";
        int result = new Solution0434().countSegments0(s);
        System.out.println(result);
    }
}
