package leetcode.recall;

import line.array.Array;

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

    List<List<Integer>> res = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        if(n == 0 || k == 0) return null;
        if(n == 1) {
            List<Integer> subRes = new LinkedList<Integer>();
            subRes.add(1);
            res.add(subRes);
            return res;
        }
        if(k == 1) {
            for(int i = 1; i < n; i++) {
                List<Integer> subRes = new LinkedList<>();
                subRes.add(i);
                res.add(subRes);
            }
            return res;
        }
        for(int i = 1; i < n; i++) {
            ArrayList<Integer> subRes = new ArrayList<Integer>(k);
            subRes.add(i);
            parse(n, k, i, subRes);
        }

        return res;
    }

    private void parse(int n, int k, int num, ArrayList<Integer> subRes) {
        if(subRes.size() == k) {
            res.add(new ArrayList<>(subRes));
            return;
        }
        for(int i = num; i < n; i++) {
            subRes.add(i + 1);
            parse(n, k, i + 1, subRes);
            subRes.remove(Integer.valueOf(i + 1));
        }
    }

}
