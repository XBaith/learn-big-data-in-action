package tree.bst;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 二分搜索树
 *
 * @param <E>
 * @author tocreation
 */
public class BinarySearchTree<E extends Comparable<E>> {
    private class Node {
        E e;
        Node left, right;

        Node() {
        }

        Node(E e) {
            this.e = e;
        }
    }

    private Node root;
    private int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void insert(E e) {
        root = insert(root, e);
    }

    private Node insert(Node root, E e) {
        if (root == null) {
            size++;
            return new Node(e);
        }

        int cmp = e.compareTo(root.e);

        if (cmp > 0) {
            root.right = insert(root.right, e);
        } else if (cmp < 0) {
            root.left = insert(root.left, e);
        }

        return root;
    }

    public boolean contains(E e) {
        if (isEmpty()) {
            throw new RuntimeException("Tree is empty.");
        }
        return contains(root, e);
    }

    private boolean contains(Node root, E e) {
        if (root == null) {
            return false;
        }

        int cmp = e.compareTo(root.e);

        if (cmp > 0) {
            return contains(root.right, e);
        } else if (cmp < 0) {
            return contains(root.left, e);
        } else {
            return true;
        }
    }

    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node root) {
        if (root == null) {
            return;
        }
        System.out.print(root.e + "\t");
        preOrder(root.left);
        preOrder(root.right);
    }

    public void preOrderNR() {
        preOrderNR(root);
        System.out.println();
    }

    private void preOrderNR(Node root) {
        Stack<Node> stack = new Stack<>();
        stack.push(this.root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.print(node.e + "\t");

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
    }

    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        System.out.print(root.e + "\t");
        inOrder(root.right);
    }

    public void inOderNR(Node root) {
        Stack<Node> stack = new Stack();
        Node cur = root;
        while(cur != null || !stack.isEmpty()) {
            while(cur != null) {
                stack.push(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            System.out.println(cur.e);
            cur = cur.right;
        }
    }

    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node root) {
        if (root == null) {
            return;
        }
        inOrder(root.left);
        inOrder(root.right);
        System.out.print(root.e + "\t");
    }

    public void levelOrder() {
        levelOrder(root);
        System.out.println();
    }

    private void levelOrder(Node root) {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            System.out.print(node.e + "\t");

            if (node.left != null) {
                queue.add(node.left);
            }
            if (node.right != null) {
                queue.add(node.right);
            }
        }
    }

    public E findMin() {
        if (isEmpty()) {
            throw new RuntimeException("Tree is empty.");
        }
        return findMin(root).e;
    }

    private Node findMin(Node node) {
        if (node.left != null) {
            node = findMin(node.left);
        }
        return node;
    }

    public E findMinNR() {
        if (isEmpty()) {
            throw new RuntimeException("Tree is empty.");
        }
        return findMinNR(root).e;
    }

    private Node findMinNR(Node node) {
        //Node minNode = null;
        while (node != null) {
            //minNode = node;
            if (node.left == null) {
                return node;
            } else {
                node = node.left;
            }
        }
        return node;
    }

    public E findMax() {
        if (isEmpty()) {
            throw new RuntimeException("Tree is empty.");
        }
        return findMax(root).e;
    }

    private Node findMax(Node node) {
//        if (node == null) {
//            return null;
//        }
        if (node.right != null) {
            node = findMax(node.right);
        }
        return node;
    }

    public E findMaxNR() {
        if (isEmpty()) {
            throw new RuntimeException("Tree is empty.");
        }
        return findMaxNR(root).e;
    }

    private Node findMaxNR(Node node) {
        //Node maxNode = null;
        while (node != null) {
            //maxNode = node;
            if (node.right == null) {
                return node;
            } else {
                node = node.right;
            }
        }
        return node;
    }

    public E removeMin(){
        E min = findMin();
        root = removeMin(root);

        return min;
    }

    private Node removeMin(Node node) {
        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }
        node.left = removeMin(node.left);

        return node;
    }

    public E removeMax(){
        E max = findMax();
        root = removeMax(root);

        return max;
    }

    private Node removeMax(Node node) {
        if(node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }
        node.right = removeMax(node.right);

        return node;
    }

    public void remove(E e){
        if(isEmpty()){
            throw new IllegalArgumentException("Tree is Empty.");
        }
        root = removeNode(root, e);
    }

    private Node leftIsNull(Node node){
        Node rightNode = node.right;
        node.right = null;
        size--;
        return rightNode;
    }

    private Node rightIsNull(Node node){
        Node leftNode = node.left;
        node.left = null;
        size--;
        return leftNode;
    }

    private Node remove(Node node, E e) {
        if(root == null){
            return null;
        }

        if(e.compareTo(node.e) < 0){
            node.left = remove(node.left, e);
            //return node;
        }else if(e.compareTo(node.e) > 0){
            node.right = remove(node.right, e);
        }else{
            if(node.left == null){
                return leftIsNull(node);
            }
            if(node.right == null){
                return rightIsNull(node);
            }

            Node successor = removeMin(node.right);
            successor.left = node.left;
            node.left = node.right = null;

            return successor;
        }
        return node;
    }

    private Node removeNode(Node node, E e){
        if(node == null){
            return null;
        }
        int cmp = e.compareTo(node.e);

        if(cmp < 0){
            node.left = remove(node.left, e);
        }else if(cmp > 0){
            node.right = remove(node.right, e);
        }else if(node.left != null && node.right != null){
            node.e = findMin(node.right).e;
            node.right = remove(node.right, node.e);
        }else{
            return (node.left != null) ? node.left : node.right;
        }
        return node;
    }
}
