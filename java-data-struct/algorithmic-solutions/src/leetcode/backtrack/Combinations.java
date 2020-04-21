package leetcode.backtrack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/combinations/
 * 给定两个整数 n 和 k，返回 1 ... n 中所有可能的 k 个数的组合。
 *
 * 示例:
 * 输入: n = 4, k = 2
 * 输出:
 * [
 *   [2,4],
 *   [3,4],
 *   [2,3],
 *   [1,2],
 *   [1,3],
 *   [1,4],
 * ]
 */
public class Combinations {

    List<List<Integer>> res = new LinkedList<>();

    public List<List<Integer>> combine(int n, int k) {
        if(n == 0 || k == 0) return res;
        if(n == 1) {
            List<Integer> subRes = new LinkedList<Integer>();
            subRes.add(1);
            res.add(subRes);
            return res;
        }
        if(k == 1) {
            for(int i = 1; i <= n; i++) {
                List<Integer> subRes = new LinkedList<>();
                subRes.add(i);
                res.add(subRes);
            }
            return res;
        }
        backtrack(n, k, 1, new LinkedList<>());

        return res;
    }

    private void backtrack(int n, int k, int startWith, LinkedList<Integer> subRes) {
        if(subRes.size() == k) {
            res.add(new LinkedList<>(subRes));
            return;
        }
        //还有k - subRes.size()个空位
        //说明[i...n]至少有k - subRes.size()个结果
        //k - subRes.size() = 1 => i = n - 1
        //k - subRes.size() = 2 => i = n - 2
        //...
        //k - subRes.size() = k => i = n - (k - subSize()) + 1
        for(int i = startWith; i <= n - (k - subRes.size()) + 1; i++) {
            subRes.add(i);
            backtrack(n, k, i + 1, subRes);
            subRes.removeLast();
        }
    }

}
