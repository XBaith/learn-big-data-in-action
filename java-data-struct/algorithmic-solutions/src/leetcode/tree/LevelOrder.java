package leetcode.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 * 给定一个二叉树，返回其按层次遍历的节点值。 （即逐层地，从左到右访问所有节点）。
 * 例如:
 * 给定二叉树: [3,9,20,null,null,15,7],
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回其层次遍历结果：
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 */
public class LevelOrder {
    List<List<Integer>> res = new ArrayList<>();

    /**
     * 递归求解
     * @param node
     * @param level
     */
    public void helper(TreeNode node, int level) {
        if(res.size() == level) res.add(new LinkedList<>());

        res.get(level).add(node.val);

        if(node.left != null) helper(node.left, level + 1);
        if(node.right != null) helper(node.right, level + 1);
    }

    public List<List<Integer>> DFSLevelOrder(TreeNode root) {
        if(root == null) return res;
        helper(root, 0);
        return res;
    }

    /**
     * 用BFS解决
     * @param root
     * @return
     */
    public List<List<Integer>> BFSLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if(root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int level = 0;
        while(!queue.isEmpty()) {
            res.add(new LinkedList<>());
            int nodeSize = queue.size();
            //递归这一层所有的节点
            for(int i = 0; i < nodeSize; i++) {
                TreeNode node = queue.poll();
                res.get(level).add(node.val);

                if(node.left != null) queue.add(node.left);
                if(node.right != null) queue.add(node.right);
            }
            level++;
        }
        return res;
    }
}
