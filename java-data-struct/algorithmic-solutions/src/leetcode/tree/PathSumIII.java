package leetcode.tree;

/**
 * https://leetcode-cn.com/problems/path-sum-iii/
 * 给定一个二叉树，它的每个结点都存放着一个整数值。
 * 找出路径和等于给定数值的路径总数。
 * 路径不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。
 * 二叉树不超过1000个节点，且节点数值范围是 [-1000000,1000000] 的整数。
 *
 * 示例：
 * root = [10,5,-3,3,2,null,11,3,-2,null,1], sum = 8
 *       10
 *      /  \
 *     5   -3
 *    / \    \
 *   3   2   11
 *  / \   \
 * 3  -2   1
 *
 * 返回 3。和等于 8 的路径有:
 * 1.  5 -> 3
 * 2.  5 -> 2 -> 1
 * 3.  -3 -> 11
 */
public class PathSumIII {

    private int sum;

    /**
     * 检查所有结点作为路径终点时的情况。即每遍历到一个结点，就以它为路径终点，向上回溯到根结点，
     * 同时在这个回溯的过程中不断累加顶点的和，相当于确定了终点，然后在向上回溯的过程中，
     * 把遇到的每个结点都尝试作为路径的起点，计算路径和，
     * 判断是否等于给定值，从而得到当前这个结点作为终点时的路径总数。
     * @param root
     * @param sum
     * @return
     */
    public int pathSum(TreeNode root, int sum) {
        if(root == null) return 0;

        this.sum = sum;
        return pathSum(root, new int[1000], 0);
    }

    private int pathSum(TreeNode root, int[] ints, int level) {
        if(root == null) return 0;

        ints[level] = root.val;
        int cur = 0;
        int sumTemp = 0;
        for(int i = level; i >= 0; --i) {
            sumTemp += ints[i];
            if(sumTemp == this.sum) cur++;
        }

        return cur + pathSum(root.left, ints, level + 1) + pathSum(root.right, ints, level + 1);
    }

}
