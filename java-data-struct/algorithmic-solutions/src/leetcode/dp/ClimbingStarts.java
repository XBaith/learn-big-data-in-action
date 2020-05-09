package leetcode.dp;

/**
 * https://leetcode-cn.com/problems/climbing-stairs/
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 * 注意：给定 n 是一个正整数。
 *
 * 示例 1：
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 *
 * 示例 2：
 * 输入： 3
 * 输出： 3
 * 解释： 有三种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶 + 1 阶
 * 2.  1 阶 + 2 阶
 * 3.  2 阶 + 1 阶
 *
 * 4
 * 1 + 1 + 1 + 1
 * 1 + 1 + 2
 * 1 + 2 + 1
 * 2 + 1 + 1
 * 2 + 2
 */
public class ClimbingStarts {

    /**
     * c(n) = 1 + c(n - 1)  //子问题1
     * c(n) = 2 + c(n - 2)  //子问题2
     * 再去求解2个子问题
     * c(n - 1) = 1 + c(n - 2)
     * c(n - 1) = 2 + c(n - 3)
     *
     * c(n - 2) = 1 + c(n - 3)
     * c(n - 2) = 2 + c(n - 4)
     * 因此，对于方法个数来说，常数1或2可以不影响结果，所以最终可以看出：
     * c(n)的上楼方法数 = c(n-1)的上楼方法数 + c(n-2)的上楼方法数
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if(n == 1) return 1;
        int[] memo = new int[n + 1];
        memo[1] = 1;
        memo[2] = 2;
        for(int i = 3; i <= n; i++) {
            memo[i] = memo[i - 1] + memo[i - 2];
        }
        return memo[n];
    }

}
