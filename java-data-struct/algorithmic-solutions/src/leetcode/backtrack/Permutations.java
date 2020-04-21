package leetcode.backtrack;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 */
public class Permutations {

    List<List<Integer>> res = new ArrayList<>();
    boolean[] used;

    public List<List<Integer>> permute(int[] nums) {
        if(nums.length == 0) return null;
        used = new boolean[nums.length];
        parseNum(nums, 0, new LinkedList<Integer>());
        return res;
    }

    private void parseNum(int[] nums, int index, LinkedList<Integer> subRes) {
        if(index == nums.length){
            res.add(new LinkedList<>(subRes));
            return;
        }

        for(int i = 0; i < nums.length; i++) {
            if(!used[i]) {
                used[i] = true;
                subRes.add(nums[i]);
                parseNum(nums, index + 1, subRes);
                subRes.removeLast();
                used[i] = false;
            }
        }
    }

    public static void main(String[] args) {
        Permutations permutations = new Permutations();
        List<List<Integer>> res = permutations.permute(new int[]{1,2,3});
        for(List<Integer> subRes : res) {
            System.out.println(subRes);
        }
    }
}
