package leetcode.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 * 例如，给出
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 * 返回如下的二叉树：
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 */
public class PreorderAndInorder {

    private int rootIndex = 0;
    /**
     *  优化点：每次根据前序遍历数组找到根节点后，为了分为左右子树，需要再在中序遍历中找到索引
     *         因此可以用Map来存放中序的值与索引的对应关系，省去多次遍历的时间消耗
     */

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length == 0 || inorder.length == 0)
            return null;

        int rootVal = preorder[0];
        int mid = 0;
        for(int i = 0; i < inorder.length; i++) {
            if(rootVal == inorder[i]) mid = i;
        }

        return buildTree(preorder, inorder, 0, inorder.length);
    }

    private TreeNode buildTree(int[] preorder, int[] inorder, int left, int right) {
        if(right == left || rootIndex >= inorder.length) return null;

        TreeNode root = new TreeNode(preorder[rootIndex]);
        int mid = left;
        for(int i = left; i < right; i++) {
            if(inorder[i] == root.val) mid = i;
        }
        rootIndex++;
        root.left = buildTree(preorder, inorder, left, mid);
        root.right = buildTree(preorder, inorder, mid + 1, right);

        return root;
    }

    /**
     * [3,9,20,15,7]
     * [9,3,15,20,7]
     * @param args
     */
    public static void main(String[] args) {
        PreorderAndInorder pai = new PreorderAndInorder();
        TreeNode root = pai.buildTree(new int[]{3,9,20,15,7}, new int[]{9,3,15,20,7});

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        System.out.print(root.val + " ");
        while(!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if(node.left != null) {
                System.out.print(node.left.val + " ");
                queue.add(node.left);
            }
            if(node.right != null){
                System.out.print(node.right.val + " ");
                queue.add(node.right);
            }
        }
    }

}
