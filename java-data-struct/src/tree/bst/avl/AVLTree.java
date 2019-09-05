package tree.bst.avl;

import java.util.ArrayList;
import java.util.List;

public class AVLTree<K extends Comparable<K>, V> {
    private class Node {
        K key;
        V value;
        int height;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            Node left = right = null;
            height = 1;
        }
    }
//     根节点
    private Node root;
//    节点总数
    private int size;

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {
        if (node == null) {
            size++;
            return new Node(key, value);
        }
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else {
            node.value = value;
        }
        //根据二分搜索树的性质添加完新节点后，对当前节点进行平衡维护
        return balance(node);
    }
    //维护节点平衡性
    private Node balance(Node node){
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        int factor = getBalanceFactor(node);
        int leftFactor = getBalanceFactor(node.left);
        int rightFactor = getBalanceFactor(node.right);
        //LL，添加节点在不平衡节点左侧的左侧，左边子树高度大于右边
        if (factor > 1 && leftFactor >= 0) {
            return rightRotate(node);
        }
        //RR
        if(factor < -1 && rightFactor <= 0) {
            return leftRotate(node);
        }
        //LR
        if(factor > 1 && leftFactor < 0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        //RL
        if(factor < -1 && rightFactor > 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
//    右旋转
    private Node rightRotate(Node unbalance){
        Node leftChild = unbalance.left;
        Node temp = leftChild.right;
        leftChild.right = unbalance;
        unbalance.left = temp;

        unbalance.height = Math.max(getHeight(unbalance.left), getHeight(unbalance.right)) + 1;
        leftChild.height = Math.max(getHeight(leftChild.left), getHeight(leftChild.right)) + 1;

        return leftChild;
    }
//    左旋转
    private Node leftRotate(Node unbalance){
        Node rightChild = unbalance.right;
        Node temp = rightChild.left;
        rightChild.left = unbalance;
        unbalance.right = temp;

        unbalance.height = Math.max(getHeight(unbalance.left), getHeight(unbalance.right)) + 1;
        rightChild.height = Math.max(getHeight(rightChild.left), getHeight(rightChild.right)) + 1;

        return rightChild;
    }

    private int getHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }
//    计算平衡因子
    private int getBalanceFactor(Node node) {
        if (node == null) {
            return 0;
        }
        //平衡因子正负可以表示左右子树的高低
        return getHeight(node.left) - getHeight(node.right);
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key) {
        if (node == null) {
            return null;
        }

        if (key.equals(node.key)) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else // if(key.compareTo(node.key) > 0)
        {
            return getNode(node.right, key);
        }
    }

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException(key + " doesn't exist!");
        }

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

//  左子树为空，返回右节点
    private Node leftNodeNull(Node node) {
        Node rightNode = node.right;
        node.right = null;
        size--;
        return rightNode;
    }
//  右子树为空，返回左节点
    private Node rightNodeNull(Node node) {
        Node leftNode = node.left;
        node.left = null;
        size--;
        return leftNode;
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node) {
        if (node.left == null) {
            return leftNodeNull(node);
        }
        node.left = removeMin(node.left);
        return balance(node);
    }

    /**
     * 从二分搜索树中删除键为key的节点
     * @return V
     */
    public V remove(K key) {
        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if (node == null) {
            return null;
        }
        Node retNode = null;
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {
            // 待删除节点左子树为空的情况
            if (node.left == null) {
                retNode = leftNodeNull(node);
            }
            // 待删除节点右子树为空的情况
            else if (node.right == null) {
                retNode = rightNodeNull(node);
            }else {
            /*
             待删除节点左右子树均不为空的情况，找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点，用这个节点顶替待删除节点的位置
             */
                Node successor = minimum(node.right);
                successor.right = removeMin(node.right);
                successor.left = node.left;
//            把删除节点左右子树置空
                node.left = node.right = null;

                retNode = successor;
            }
        }
        if(retNode == null){
            return null;
        }
        //维持节点平衡
        return balance(retNode);
    }

    /**
     * 确认Tree是否符合二分搜索树性质
     * @return 是否符合
     */
    public boolean isBinarySearch(){
        List<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for(int i = 1 ; i < keys.size(); i++){
            if(keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    private void inOrder(Node node, List<K> keys) {
        if(node == null){
            return;
        }
        inOrder(node.left,keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    /**
     * 确认Tree是否符合平衡二叉树性质
     * @return 是否符合
     */
    public boolean isBalanced(){
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        if(node == null){
            return true;
        }
        int balanceFactor = getBalanceFactor(node);
        if(balanceFactor > 1){
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

}
