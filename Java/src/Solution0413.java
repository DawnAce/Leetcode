/**
 * 数组 A 包含 N 个数，且索引从0开始。数组 A 的一个子数组划分为数组 (P, Q)，P 与 Q 是整数且满足 0<=P<Q<N 。
 *
 * 如果满足以下条件，则称子数组(P, Q)为等差数组：
 *
 * 元素 A[P], A[p + 1], ..., A[Q - 1], A[Q] 是等差的。并且 P + 1 < Q 。
 *
 * 函数要返回数组 A 中所有为等差数组的子数组个数。
 */
public class Solution0413 {
    //这段代码非常精简，且逻辑很清楚！
    public int numberOfArithmeticSlices(int[] A) {
        int curr = 0, sum = 0;
        for (int i=2; i<A.length; i++)
            if (A[i]-A[i-1] == A[i-1]-A[i-2]) {
                curr += 1;
                sum += curr;
            } else {
                curr = 0;
            }
        return sum;
    }

    //注意，题目是要计算连续的，所以直接进行比那里，找到以某个index开始后的最长等差序列长度进行计算，然后从不满足的index开始重新计算
    //代码功力还需提升，太长了，很难看
    public int numberOfArithmeticSlices0(int[] A) {
        int result = 0, len = 0, dis = Integer.MIN_VALUE;
        for(int i=0; i<A.length; ++i){
            if(len == 0){
                //长度不够，直接跳出即可
                if(i+2 >= A.length) break;
                if(A[i+1]-A[i] == A[i+2]-A[i]){
                    len = 3;
                    dis = A[i+1] - A[i];
                    i += 2;  //指针移动到目前序列的最后一个数上
                }
            }else if(A[i]-A[i-1] == dis){
                ++len;
            }else{
                result += (1+len-2)*(len-2)/2;
                len = 0;
                dis = Integer.MIN_VALUE;
                --i;  //需要从本位开始重新计算
            }
        }
        if(len != 0) result += (1+len-2) * (len-2) / 2;
        return result;
    }

    public static void main(String[] args) {
        int[] A =
                {1,2,3,5,6,7};
        int result = new Solution0413().numberOfArithmeticSlices(A);
        System.out.println(result);
    }
}
