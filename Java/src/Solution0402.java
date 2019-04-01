import java.util.Stack;

/**
 * 给定一个以字符串表示的非负整数 num，移除这个数中的 k 位数字，使得剩下的数字最小。
 *
 * 注意:
 *
 * num 的长度小于 10002 且 ≥ k。
 * num 不会包含任何前导零。
 */
public class Solution0402 {
    //核心思路跟我的一样，但是使用数组模拟堆栈，且代码简洁太多！
    //有一点不一样，我是用堆栈保持需要删除的候选项，这个方法使用堆栈保持最后的值
    public String removeKdigits(String num, int k) {
        int total = num.length() - k;
        if (total == 0) return "0";
        char[] stk = new char[num.length()];    //使用堆栈保持需要的字符
        int top = 0;

        for (int i=0; i<num.length(); i++) {
            char c = num.charAt(i);
            while (top>0 && stk[top-1] > c && k > 0) {
                top--;
                k--;
            }
            stk[top++] = c;
        }
        //System.out.println(new String(stk));
        int idx = 0;
        while (idx < total && stk[idx] == '0') idx++;
        return idx==total? "0" : new String(stk, idx, total - idx);
    }

    //一开始其实是找规律发现，删除的数字都是峰值，然后就想办法证明它
    //首先假设删除的峰值不会导致前导0，那么删掉一个值，数字长度减少1，要使得数最小
    //假设峰值形式为ahib,显然如果删除了左侧除峰值外的数，由于后面的数大于前面的数，一定有删除的数越大，数越小
    //如果删除的为右侧峰值外的数，删除的数越大，数越小，综上峰值为拐点，所以删除峰值数将最小
    //这里需要特别注意前导0的情况，前导0应该删除，但是不应该占用k的次数
    //这里延伸一下，如果要使删除后的数最大，显然应该从后开始删除峰谷
    public String removeKdigits0(String num, int k) {
        if(num.length() <= k) return "0";
        else if(k == 0) return num;
        //只记录递增的序列
        Stack<Integer> stack = new Stack<>();
        boolean[] remove = new boolean[num.length()];
        for(int i=0; i<num.length(); ++i){
            char c = num.charAt(i);
            //如果目前递增序列为空，且当前值为0，则本位为前导0，应该删除
            if(stack.size()==0 && c=='0') remove[i] = true;
            else if(stack.size()==0 || num.charAt(stack.peek())<=c)
                stack.push(i);
            else {
                //这一块可以通过出栈peek值，减少i的方式简化代码，但是会造成多次读取i为的char，没有太大必要
                while(stack.size()>0 &&
                        num.charAt(stack.peek())>c && k-->0)
                    remove[stack.pop()] = true;
                if(stack.size()==0 && c=='0')
                    remove[i] = true;
                else stack.push(i);
            }
        }
        //如果删除的位数不够，那么需要将堆栈中的递增序列从大到小删除
        while(k-->0 && stack.size()>0)
            remove[stack.pop()] = true;
        StringBuilder result = new StringBuilder();

        for(int i=0; i<num.length(); ++i)
            if (!remove[i]) result.append(num.charAt(i));
        return result.length()==0 ? "0" : result.toString();
    }

    //下面的方法跟上面除了使用boolean判断字符是否都为0和循环中的条件不一样外，其他都一样，但是快一倍，搞不懂
    public String removeKdigits1(String num, int k) {
        if(num.length() <= k) return "0";
        else if(k == 0) return num;
        //只记录递增的序列
        Stack<Integer> stack = new Stack<>();
        boolean[] remove = new boolean[num.length()];
        for(int i=0; i<num.length() && k>0; ++i){   //循环条件中加入对k的判断
            char c = num.charAt(i);
            if(stack.size()==0 && c=='0') remove[i] = true;
            else if(stack.size()==0 || num.charAt(stack.peek())<=c)
                stack.push(i);
            else {
                while(stack.size()>0 &&
                        num.charAt(stack.peek())>c && k-->0)
                    remove[stack.pop()] = true;
                if(stack.size()==0 && c=='0')
                    remove[i] = true;
                else stack.push(i);
            }
        }
        while(k-->0 && stack.size()>0)
            remove[stack.pop()] = true;
        StringBuilder result = new StringBuilder();
        boolean allZero = true;     //使用boolean判断是否数字都为0
        for(int i=0; i<num.length(); ++i) {
            if (!remove[i]) {
                result.append(num.charAt(i));
                if(num.charAt(i) != '0') allZero = false;
            }
        }
        return allZero ? "0" : result.toString();
    }

    public static void main(String[] args) {
        String num =
                "1020000";
        int k = 2;
        String result = new Solution0402().removeKdigits(num, k);
        System.out.println(result);
    }
}
