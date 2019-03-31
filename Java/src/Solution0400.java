/**
 * 在无限的整数序列 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...中找到第 n 个数字。
 *
 * 注意:
 * n 是正数且在32为整形范围内 ( n < 231)。
 */
public class Solution0400 {
    //先看n最后出现在几位数中,然后计算出在当前位数的数中排在第几位(因为当前位数的第一个数都为1***)
    //所以知道了在当前位数中排第几位，就知道了最终结果出现在哪个数字之中，然后就可以计算出来具体数字了
    //这样其实就有点类似于将数组flat，然后找数字
    //row[1]:1,2,3,4,5,6,7,8,9,共9个数，占9*1个字符长度
    //row[2]:10,11,12,...99,共90个数，占90*2个字符长度
    //依次类推，先找到所属行数，然后找到所属列数，最后根据位数找到最终结果
    //这里需要特别注意越界的问题，所以比较时需要转化为long
    public int findNthDigit(int n) {
        int start = 1, total = 9, bits = 1;
        while(n > (long)total*bits){
            n -= total*bits;
            total *= 10;
            start *= 10;
            ++bits;
        }
        String result = String.valueOf(start + (n-1)/bits);
        //因为当前数字有bits位，所以采用%运算就能知道是哪个数字了
        return result.charAt((n-1)%bits)-'0';
    }

    public static void main(String[] args) {
        int n = 2147483647;
        int result = new Solution0400().findNthDigit(n);
        System.out.println(result);
    }
}
