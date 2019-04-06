/**
 * 给定一个非空字符串，其中包含字母顺序打乱的英文单词表示的数字0-9。按升序输出原始的数字。
 *
 * 注意:
 *
 * 输入只包含小写英文字母。
 * 输入保证合法并可以转换为原始的数字，这意味着像 "abc" 或 "zerone" 的输入是不允许的。
 * 输入字符串的长度小于 50,000。
 */
public class Solution0423 {
    //这个方法是下面方法的精简版
    public String originalDigits(String s) {
        // building hashmap letter -> its frequency
        char[] count = new char[26];
        for(char letter: s.toCharArray()) {
            count[letter - 'a']++;
        }

        int[] out = new int[10];
        out[0] = count['z' - 'a'];
        out[2] = count['w' - 'a'];
        out[4] = count['u' - 'a'];
        out[6] = count['x' - 'a'];
        out[8] = count['g' - 'a'];
        out[3] = count['h' - 'a'] - out[8];
        out[5] = count['f' - 'a'] - out[4];
        out[7] = count['s' - 'a'] - out[6];
        out[9] = count['i' - 'a'] - out[5] - out[6] - out[8];
        out[1] = count['n' - 'a'] - out[7] - 2 * out[9];

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < out[i]; j++){
                sb.append(i);
            }
        }
        return sb.toString();

    }

    //核心思路是根据字符的唯一性进行判断解析
    public String originalDigits0(String s) {
        int[] record = new int[26], result = new int[10];
        for(char c : s.toCharArray())
            ++record[c-'a'];
        //z,x和w是独有的
        result[0] = record['z'-'a'];    //zero
        record['e'-'a'] -= result[0];
        record['r'-'a'] -= result[0];
        record['o'-'a'] -= result[0];
        result[6] = record['x'-'a'];    //six
        record['s'-'a'] -= result[6];
        record['i'-'a'] -= result[6];
        result[2] = record['w'-'a'];    //two
        record['t'-'a'] -= result[2];
        record['o'-'a'] -= result[2];

        //u只存在于four
        result[4] = record['u'-'a'];
        record['o'-'a'] -= result[4];
        record['u'-'a'] -= result[4];
        record['r'-'a'] -= result[4];

        //现在o只出现于one中
        result[1] = record['o'-'a'];
        record['n'-'a'] -= result[1];
        record['e'-'a'] -= result[1];

        //s由于只出现于six和seven中，所以目前seven可以计算了
        result[7] = record['s'-'a'];
        record['e'-'a'] -= 2*result[7];
        record['v'-'a'] -= result[7];
        record['n'-'a'] -= result[7];

        //v只存在于five和seven中，所以five可以计算了
        result[5] = record['v'-'a'];
        record['f'-'a'] -= result[5];
        record['i'-'a'] -= result[5];
        record['e'-'a'] -= result[5];

        //r只存在于four和three中
        result[3] = record['r'-'a'];
        record['t'-'a'] -= result[3];
        record['h'-'a'] -= result[3];
        record['e'-'a'] -= 2*result[3];

        //e -> eight
        result[8] = record['e'-'a'];
        record['i'-'a'] -= result[8];
        record['g'-'a'] -= result[8];
        record['h'-'a'] -= result[8];
        record['t'-'a'] -= result[8];

        //night
        result[9] = record['i'-'a'];
        record['n'-'a'] -= result[9];

        StringBuilder sb = new StringBuilder();
//        Map<Integer, String> map = new HashMap<>();
//        map.put(0, "zero");
//        map.put(1, "one");
//        map.put(2, "two");
//        map.put(3, "three");
//        map.put(4, "four");
//        map.put(5, "five");
//        map.put(6, "six");
//        map.put(7, "seven");
//        map.put(8, "eight");
//        map.put(9, "night");

        for(int i=0; i<10; ++i){
            while(result[i]-- > 0)
                sb.append(i);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s =
                "ereht";
        String result = new Solution0423().originalDigits(s);
        System.out.println(result);
    }
}
