package tree.bst.avl;

/**
 * 在多次添加更新，删除操作时，红黑树比其AVL更有优势
 * @param <K>
 * @param <V>
 */
public class RedBlackTree<K extends Comparable<K>, V> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node{
        K key;
        V value;
        Node left, right;
        boolean color;
        Node(K key, V value){
            this.key = key;
            this.value = value;
            left = right = null;
            color = RED;
        }
    }

    private Node root;
    private int size;

    public RedBlackTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isRed(Node node){
        if(node == null){
            return BLACK;
        }
        return node.color;
    }

    // 向RB树中添加新的元素(key, value)
    public void add(K key, V value){
        root = add(root, key, value);
        root.color = BLACK;
    }

    // 向以node为根的RB树中插入元素(key, value)，递归算法
    private Node add(Node node, K key, V value){
        if(node == null){
            size ++;
            return new Node(key, value);
        }

        if(key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;

        //节点右子树有红色节点
        if(isRed(node.right) && !isRed(node.left)){
            node = leftRotate(node);
        }
        //左倾斜
        if(isRed(node.left) && isRed(node.left.left)){
            node = rightRotate(node);
        }
        //左右子树为红
        if(isRed(node.left) && isRed(node.right)){
            flipColors(node);
        }
        //返回维护后的节点，返回上一层递归或退出递归
        return node;
    }

    /*
       node                     x
      /   \     左旋转         /  \
     T1   x   --------->   node   T3
         / \              /   \
        T2 T3            T1   T2
     */
    private Node leftRotate(Node node){
        Node x = node.right;
        //左旋转
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RED;

        return x;
    }

    /**
     * 当node左右子树都为红色时，即形成了临时的4节点，需要通过反转颜色完成拆分和向上融合的步骤
     * @param node
     */
    private void flipColors(Node node){
        node.left.color = node.right.color = BLACK;
        node.color = RED;
    }

    /*
       node                     x
      /   \     右旋转         /  \
     x    T3   --------->    T1  node
    / \                          / \
   T1 T2                        T2 T3
     */
    private Node rightRotate(Node node){
        Node x = node.left;
        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key){

        if(node == null)
            return null;

        if(key.equals(node.key))
            return node;
        else if(key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else // if(key.compareTo(node.key) > 0)
            return getNode(node.right, key);
    }

    public boolean contains(K key){
        return getNode(root, key) != null;
    }

    public V get(K key){

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){
        if(node.left == null){
           return leftIsNull(node);
        }

        node.left = removeMin(node.left);
        return node;
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

    // 从二分搜索树中删除键为key的节点
    public V remove(K key){
        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key){
        if( node == null )
            return null;

        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            return node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
            return node;
        }
        else{   // key.compareTo(node.key) == 0
            // 待删除节点左子树为空的情况
            if(node.left == null){
                return leftIsNull(node);
            }

            // 待删除节点右子树为空的情况
            if(node.right == null){
                return rightIsNull(node);
            }
            // 待删除节点左右子树均不为空的情况
            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }

}
