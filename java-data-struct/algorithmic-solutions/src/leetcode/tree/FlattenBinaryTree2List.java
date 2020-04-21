package leetcode.tree;

import java.util.LinkedList;
import java.util.List;

/**
 * <a>https://leetcode-cn.com/problems/flatten-binary-tree-to-linked-list/</a>
 * 给定一个二叉树，原地将它展开为链表。
 * 例如，给定二叉树
 *     1
 *    / \
 *   2   5
 *  / \   \
 * 3   4   6
 * 将其展开为：
 * 1
 *  \
 *   2
 *    \
 *     3
 *      \
 *       4
 *        \
 *         5
 *          \
 *           6
 */
public class FlattenBinaryTree2List {

    /**
     * 直接将左子树放到右子树，找到左子树的最右节点，之后将原本的右子树链入最右节点
     * @param root
     */
    public void myFlatten(TreeNode root) {
        if(root == null) return;
        if(root.left == null)
            myFlatten(root.right);
        else {
            TreeNode mostRight = root.left;
            while (mostRight.right != null)
                mostRight = mostRight.right;

            TreeNode right = root.right;
            root.right = root.left;
            root.left = null;
            mostRight.right = right;

            myFlatten(root.right);
        }
    }

    /**
     * 题目是先序遍历，
     * 1 2 3 4 5 6
     * 可以想到遍历到1时, 1->2 3 4 5 6
     * 遍历到2时, 1->2->3 4 5 6
     * ...
     * 但是会丢失右子树的引用
     *
     * 可以利用后序遍历，先将上一次遍历到的节点引用用节点pre存下，当回到父节点时就将right指针指向pre
     * 6 5 4 3 2 1
     * 6<-5 4 3 2 1
     * 6<-5<-4 3 2 1
     * ...
     * @param root
     */
    public void flatten(TreeNode root){
        TreeNode pre = null;
        if (root == null)
            return;
        flatten(root.right);
        flatten(root.left);
        root.right = pre;
        root.left = null;
        pre = root;
    }

}
