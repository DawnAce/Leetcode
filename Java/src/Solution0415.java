/**
 * 给定两个字符串形式的非负整数 num1 和num2 ，计算它们的和。
 *
 * 注意：
 *
 * num1 和num2 的长度都小于 5100.
 * num1 和num2 都只包含数字 0-9.
 * num1 和num2 都不包含任何前导零。
 * 你不能使用任何內建 BigInteger 库， 也不能直接将输入的字符串转换为整数形式。
 */
public class Solution0415 {
    //这里需要注意一点的是，不要使用result.insert(0, c)的操作，因为这个操作每次都会将当前的数组进行copy后移一位，很慢！
    public String addStrings(String num1, String num2) {
        if(num1==null || num2==null)
            return num1==null ? num2 : num1;
        if(num1.length()==0 || num2.length()==0)
            return num1.length()==0 ? num2 : num1;
        int carry = 0, index1 = num1.length(), index2 = num2.length();
        StringBuilder result = new StringBuilder();
        while(index1>=0 || index2>=0 || carry!=0){
            if(--index1 >= 0)
                carry += num1.charAt(index1) - '0';
            if(--index2 >= 0)
                carry += num2.charAt(index2) - '0';
            result.append(carry % 10);
            result.insert(0, 1);
            carry /= 10;
        }
        return result.reverse().toString();
    }
}
