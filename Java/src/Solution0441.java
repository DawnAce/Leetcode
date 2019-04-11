/**
 * 你总共有 n 枚硬币，你需要将它们摆成一个阶梯形状，第 k 行就必须正好有 k 枚硬币。
 *
 * 给定一个数字 n，找出可形成完整阶梯行的总行数。
 *
 * n 是一个非负整数，并且在32位有符号整型的范围内。
 */
public class Solution0441 {
    //x=sqrt(2n+0.25)-0.5 => 2x = 2sqrt(2n+0.25)-1 => x = (sqrt(8n+1)-1)/2
    public int arrangeCoins(int n){
        return (int)(Math.sqrt(8l*n + 1)-1)/2;
    }

    //使用double时，有一个坏处，就是可能产生的计算近似问题，改进方法是，都改为long类型
    public int arrangeCoins0(int n) {
        int temp = (int)(Math.sqrt(2.0*n+0.25)-0.5);
        return (temp+1.5)*(temp+1.5) > n ? temp : temp+1;
    }
}
