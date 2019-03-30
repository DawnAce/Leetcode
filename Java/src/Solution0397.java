/**
 * 给定一个正整数 n，你可以做如下操作：
 *
 * 1. 如果 n 是偶数，则用 n / 2替换 n。
 * 2. 如果 n 是奇数，则可以用 n + 1或n - 1替换 n。
 * n 变为 1 所需的最小替换次数是多少？
 */
public class Solution0397 {
    //对于偶数来说，很简单，直接除以2；对于奇数来说有两个选择+1和-1
    //假设n=2k+1, 那么 (n+1)/2=k+1, (n-1)/2=k,说明k+1和k中一定有一个奇数一个偶数
    //1.假设k为奇数(k!=1),则k=2x+1,k+1=2x+2 => f(k)=min(f(2x+2),f(2x))+1, f(k+1)=1+f(x+1)
    //显然如果f(2x+2)较小，那么一定有f(k)>f(k+1)，现假设f(2x)较小，那么f(2x)=f(x)+1 => f(k)=f(x)+2
    //得到f(k+1)-f(k)=f(x+1)-f(x)-1对于最差得情况,f(x+1)>f(x),f(x+1)+1=f(x)，所以有f(k+1)-f(k)<=0
    //说明选择+1或者-1，需要选择除以2后为偶数的(当然对于3不成立，因为(3-1)/2=1，条件终止，所以3需要-1)
    //2.假设k为偶数，则k=2x, k+1=2x+1 => f(k)=f(x)+1, f(k+1)=min(f(2x+2),f(2x))+1
    //此时只有当f(2x+2)较小时，才有可以使得f(k)>f(k+1)(f(2x)较小的话,f(k+1)=f(x)+2>f(k))
    //那么f(k+1)=f(x+1)+2 => f(k+1)-f(k)=f(x+1)+1-f(x),|f(x)-f(x+1)|<=1,所有f(k+1)-f(k)>=0
    //综上，选择(n+1)/2和(n-1)/2中为偶数的那一个(3除外),其实可以简化为(n+1)和(n-1)为4的倍数的那一个
    public int integerReplacement(int n){
        if(n == 1) return 0;
        if(n == Integer.MAX_VALUE) return 32;
        if((n&1) == 0) return 1+integerReplacement(n/2);
        if(n!=3 && ((n+1)/2) % 2 == 0) return 1+integerReplacement(n+1);
        return 1+integerReplacement(n-1);
    }

    //其实每次都是去掉一个1
    public int integerReplacement0(int n) {
        if(n == 1) return 0;
        if((n&1) == 0) return 1+integerReplacement0(n/2);
        else return 1+Math.min(integerReplacement0(n-1), integerReplacement0(n+1));
    }

    //首先，1的替换次数为0，2的替换次数为1，那么3可以替换为2或者4，那么进行比较就可以知道了，所以可以用DP
    //时间复杂度为O(n),空间复杂度为O(n)，会MLE
    public int integerReplacement01(int n) {
        int[] dp = new int[n+1];
        for(int i=2; i<=n; ++i){
            if((i&1) == 0) dp[i] = dp[i/2]+1;
            else{
                //往上判断的时候(i+1)/2一定已经有记录了，但是次数得加1
                dp[i] = Math.min(dp[i-1], dp[(i+1)/2]+1)+1;
            }
        }
        return dp[n];
    }

    public static void main(String[] args) {
        int n = Integer.MAX_VALUE;
        int result = new Solution0397().integerReplacement(n);
        System.out.println(result);
    }
}
