package tree.trie;

import java.util.TreeMap;

public class Trie {

    private class Node {
        boolean isWord;
        TreeMap<Character, Node> next;

        Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        Node() {
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie() {
        root = new Node();
        size = 0;
    }

    /**
     * 向字典树中添加单词(非递归)
     *
     * @param word
     */
    public void add(String word) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if (!cur.isWord) {
            cur.isWord = true;
            size++;
        }
    }

    public void recAdd(String word){
        recAdd(word,root,0);
    }

    public void recAdd(String word, Node node, int index) {
        if(index >= word.length()){
            if(!node.isWord) {
                node.isWord = true;
                size++;
            }
            return;
        }

        char c = word.charAt(index);
        if (node.next.get(c) == null) {
            node.next.put(c, new Node());
        }else{
            recAdd(word, node.next.get(c), index + 1);
        }

    }

    public int getSize() {
        return size;
    }

}
