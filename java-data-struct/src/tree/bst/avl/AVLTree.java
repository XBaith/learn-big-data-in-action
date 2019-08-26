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
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        if (getBalanceFactor(node) > 1) {
            //树不平衡
        }
        return node;
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
        return Math.abs(getHeight(node.left) - getHeight(node.right));
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
        return node;
    }

    // 从二分搜索树中删除键为key的节点
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
        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {
            // 待删除节点左子树为空的情况
            if (node.left == null) {
                return leftNodeNull(node);
            }
            // 待删除节点右子树为空的情况
            if (node.right == null) {
                return rightNodeNull(node);
            }
            /*
             待删除节点左右子树均不为空的情况，找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点，用这个节点顶替待删除节点的位置
             */
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;
//            把删除节点左右子树置空
            node.left = node.right = null;

            return successor;
        }
    }

    private boolean isBinarySearch(){
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

    private boolean isBalance(){
        return isBalance(root);
    }

    private boolean isBalance(Node node) {
        if(node == null){
            return true;
        }
        int balanceFactor = getBalanceFactor(node);
        if(balanceFactor > 1){
            return false;
        }
        return isBalance(node.left) && isBalance(node.right);
    }

}
