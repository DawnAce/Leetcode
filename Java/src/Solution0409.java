/**
 * 给定一个包含大写字母和小写字母的字符串，找到通过这些字母构造成的最长的回文串。
 *
 * 在构造过程中，请注意区分大小写。比如 "Aa" 不能当做一个回文字符串。
 *
 * 注意:
 * 假设字符串的长度不会超过 1010。
 */
public class Solution0409 {
    //思想跟下面的方法一样，但是处理上使用了两个小技巧，所以代码简洁很多
    //1.本来奇数要加上oddCount-1，偶数都加上evenCount，这里的小技巧是count/2 * 2
    //2.如果字符串中没有出现奇数次的字符，那么最终长度应该等于字符总长度，否则说明有出现奇数次的字符，那么最终可以挑选一个奇数字符加入最终的回文
    public int longestPalindrome(String s){
        int[] record = new int[256];
        for(char c : s.toCharArray())
            ++record[c];
        int total = 0;
        for(int count : record)
            total += count/2 * 2;
        return total==s.length() ? total : total +1;
    }

    //回文串有两种形式，偶数回文或者奇数回文
    //显然如果每种字符都是偶数的话，一定能组成回文串。所以偶数直接加，奇数减1累加，最后需要确认是否全为偶数
    public int longestPalindrome0(String s) {
        int[] record = new int[256];
        for(char c : s.toCharArray())
            ++record[c];
        int total = 0, odd = 0;
        for(int count : record) {
            if ((count & 1) == 0)
                total += count;
            else {
                total += count - 1;
                odd = 1;
            }
        }
        return total + odd;
    }

    public static void main(String[] args) {
        String s =
                "civilwartestingwhetherthatnaptionoranynartionsoconceivedandsodedicatedcanlongendureWeareqmetonagreatbattlefiemldoftzhatwarWehavecometodedicpateaportionofthatfieldasafinalrestingplaceforthosewhoheregavetheirlivesthatthatnationmightliveItisaltogetherfangandproperthatweshoulddothisButinalargersensewecannotdedicatewecannotconsecratewecannothallowthisgroundThebravelmenlivinganddeadwhostruggledherehaveconsecrateditfaraboveourpoorponwertoaddordetractTgheworldadswfilllittlenotlenorlongrememberwhatwesayherebutitcanneverforgetwhattheydidhereItisforusthelivingrathertobededicatedheretotheulnfinishedworkwhichtheywhofoughtherehavethusfarsonoblyadvancedItisratherforustobeherededicatedtothegreattdafskremainingbeforeusthatfromthesehonoreddeadwetakeincreaseddevotiontothatcauseforwhichtheygavethelastpfullmeasureofdevotionthatweherehighlyresolvethatthesedeadshallnothavediedinvainthatthisnationunsderGodshallhaveanewbirthoffreedomandthatgovernmentofthepeoplebythepeopleforthepeopleshallnotperishfromtheearth";
        int result = new Solution0409().longestPalindrome(s);
        System.out.println(result);
    }
}
