import java.util.*;

/**
 * 一条基因序列由一个带有8个字符的字符串表示，其中每个字符都属于 "A", "C", "G", "T"中的任意一个。
 *
 * 假设我们要调查一个基因序列的变化。一次基因变化意味着这个基因序列中的一个字符发生了变化。
 *
 * 例如，基因序列由"AACCGGTT" 变化至 "AACCGGTA" 即发生了一次基因变化。
 *
 * 与此同时，每一次基因变化的结果，都需要是一个合法的基因串，即该结果属于一个基因库。
 *
 * 现在给定3个参数 — start, end, bank，分别代表起始基因序列，目标基因序列及基因库，
 * 请找出能够使起始基因序列变化为目标基因序列所需的最少变化次数。如果无法实现目标变化，请返回 -1。
 *
 * 注意:
 *
 * 起始基因序列默认是合法的，但是它并不一定会出现在基因库中。
 * 所有的目标基因序列必须是合法的。
 * 假定起始基因序列与目标基因序列是不一样的。
 */
public class Solution0433 {
    //这是普通的BFS方法，代码更简洁，且运行更快，快主要是快在判断能够进行转换上
    //下面的hashcode位运算更快，但是判断的时候采用的是每一位候选项的做法，所以白白多了很多计算量
    public int minMutation(String start, String end, String[] bank) {
        Queue<String> queue = new ArrayDeque<>();
        Set<String> usedStr = new HashSet<>();
        queue.add(start);
        int length = 0;
        while(!queue.isEmpty()){
            int size = queue.size();
            while(size-- > 0) {
                String str = queue.poll();
                if (str.equals(end))
                    return length;
                for (String s : bank) {
                    if (!usedStr.contains(s)) {
                        if (canTrans(str, s)) {
                            queue.offer(s);
                            usedStr.add(s);
                        }
                    }
                }
            }
            ++length;
        }
        return -1;
    }

    private boolean canTrans(String word0, String word1){
        int disCount = 0;
        for(int i=0; i<word0.length(); ++i){
            if(word0.charAt(i) != word1.charAt(i))
                ++disCount;
            if(disCount > 1)
                return false;
        }
        return true;
    }

    //因为只带8个字符，如果我能进行HashCode，那么应该是很快的
    //核心思路是利用BFS，然后从两侧向中间逼近
    //这里要注意一点，如果从两端同时逼近，有可能出现bank为0的情况，所以需要对每一个字符进行替换看是否在结果集或者字典集中
    //如果从start一方面开始，则可以直接与end进行对比，同时可以用canTrans方法判断是否能进行转化即可，不需要对每个字符进行改变
    //总的来说，如果两头逼近，那么变量为字符串中的每一个字符；如果从start逼近，那么变量为字符串
    public int minMutation0(String start, String end, String[] bank) {
        if(bank.length == 0) return 0;
        Set<Integer> banks = new HashSet<>();
        Map<Character, Integer> map = new HashMap<>();
        map.put('A', 0);
        map.put('C', 1);
        map.put('G', 2);
        map.put('T', 3);
        for(String b : bank) banks.add(code(b, map));
        if(!banks.contains(code(end, map))) return -1;
        Set<Integer> starts = new HashSet<>(Arrays.asList(code(start, map)));
        Set<Integer> ends = new HashSet<>(Arrays.asList(code(end, map)));
        return helper(starts, ends, banks, map, 0);
    }

    private int helper(Set<Integer> start, Set<Integer> end, Set<Integer> banks,
                       Map<Character, Integer> map, int level){
        if(start.size()==0 || end.size()==0) return -1;
        Set<Integer> newStart = new HashSet<>();
        for(int gene : start){
            for(int i=0; i<8; ++i){
                //这一块代码很烂，其实只要看，有两位不一样就好了，没必要每一位进行变换，看是否相等
                int origin = gene & (3<<2*i), mask = -1 - (3<<2*i);
                for(int value : map.values()){
                    if(origin == (value<<2*i)) continue;
                    int newCode = (gene&mask) | (value<<2*i);
                    if(end.contains(newCode)) return level+1;
                    if(banks.contains(newCode)){
                        newStart.add(newCode);
                        banks.remove(newCode);
                    }
                }
            }
        }
        for(int code : newStart) banks.remove(code);
        if(newStart.size() > end.size())
            return helper(end, newStart, banks, map, level+1);
        return helper(newStart, end, banks, map, level+1);
    }

    private boolean canTrans(int code0, int code1){
        int different = 0;
        for(int i=3; i<(3<<2*8); i<<=2){
            if((code0&i) != (code1&i))
                ++different;
            if(different > 1) return false;
        }
        return different == 1;
    }

    private int code(String gene, Map<Character, Integer> map){
        int result = 0;
        for(char c : gene.toCharArray())
            result = (result<<2) + map.get(c);
        return result;
    }

    public static void main(String[] args) {
        String start = "AAAAAAAA";
        String end = "CCCCCCCC";
        String[] bank = {"AAAAAAAA","AAAAAAAC","AAAAAACC","AAAAACCC","AAAACCCC","AACACCCC","ACCACCCC","ACCCCCCC","CCCCCCCA","CCCCCCCC"};
        int result = new Solution0433().minMutation0(start, end, bank);
        System.out.println(result);
    }
}
