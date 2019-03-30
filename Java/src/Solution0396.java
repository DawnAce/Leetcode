/**
 * 给定一个长度为 n 的整数数组 A 。
 *
 * 假设 Bk 是数组 A 顺时针旋转 k 个位置后的数组，我们定义 A 的“旋转函数” F 为：
 *
 * F(k) = 0 * Bk[0] + 1 * Bk[1] + ... + (n-1) * Bk[n-1]。
 *
 * 计算F(0), F(1), ..., F(n-1)中的最大值。
 *
 * 注意:
 * 可以认为 n 的值小于 105。
 */
public class Solution0396 {
    //一开始想的是，使用模拟的办法，但是这样的话，一共要进行n轮，每一轮需要进行n次计算，时间复杂度为O(n*n)
    //但是其实每一次跟前一次，有n-1个数都是进行了加自身的操作，最后一个数变为0，那么其实就是减去n*last
    //比如[4,3,2,6],第一次的结果为f(0)=0*4+1*3+2*2+3*6, f(1)=1*4+2*3+3*2+0*6=f(0)+sum-4*6 => f(2)=f(1)+sum-4*2
    //这样就有一个规律f(k) = f(k-1) + sum - n*a[n-k]
    //所以计算出来f(n)只需要进行n次计算,时间复杂度为O(n)
    public int maxRotateFunction(int[] A) {
        int sum = 0, result = 0, max = 0;
        for(int i=0; i<A.length; ++i){
            sum += A[i];
            result += i * A[i];
        }
        max = result;
        for(int i=A.length-1; i>0; --i) {
            result += sum - A.length*A[i];
            max = Math.max(max, result);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] A =
                {4, 3, 2, 6};
        int result = new Solution0396().maxRotateFunction(A);
        System.out.println(result);
    }
}
