package leetcode.dp;

/**
 * https://leetcode-cn.com/problems/longest-increasing-subsequence/
 * 给定一个无序的整数数组，找到其中最长上升子序列的长度。
 */
public class LongestIncreasingSubseq {

    /**
     * 时间复杂度：O(N^2)
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if(nums == null || nums.length == 0) return 0;

        int[] dp = new int[nums.length];
        int max = 1;

        for(int i = 0; i < nums.length; i++) {
            dp[i] = 1;  //初始化每个位置的最大序列长度，因为只有自己所以初始化为1
            for(int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    max = Math.max(dp[i], max);
                }
            }
        }

        return max;
    }

    public static void main(String[] args) {

    }
}
