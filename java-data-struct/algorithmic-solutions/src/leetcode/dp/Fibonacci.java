package leetcode.dp;

/**
 * https://leetcode-cn.com/problems/fibonacci-number/
 */
public class Fibonacci {
    /**
     * 动态规划：自下而上地解决问题
     */
    public int fib(int N) {
        if(N == 0) return 0;
        if(N == 1) return 1;
        int[] memo = new int[N + 1];
        memo[1] = 1;
        for(int i = 2; i <= N; i++)
            memo[i] = memo[i - 1] + memo[i - 2];

        return memo[N];
    }

}
