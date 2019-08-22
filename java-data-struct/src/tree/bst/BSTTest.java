package tree.bst;

import org.junit.Test;

import java.util.Stack;

public class BSTTest {
    static int nums[] = {9,5,6,2,3,1,10,15};
    @Test
    public void testBST(){
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for(int n : nums){
            bst.insert(n);
        }
        bst.preOrder();
        bst.preOrderNR();

        bst.inOrder();

        bst.postOrder();

        bst.levelOrder();
        System.out.println(bst.size());
//
//        System.out.println(bst.findMin());
//        System.out.println(bst.findMinNR());
//
//        System.out.println(bst.findMax());
//        System.out.println(bst.findMaxNR());
//
//        System.out.print(bst.removeMax() + " ");
//        System.out.println(bst.removeMax());
//        System.out.print(bst.removeMin() + " ");
//        System.out.println(bst.removeMin());
//
        bst.remove(1);
        System.out.println(bst.size());
    }

}
