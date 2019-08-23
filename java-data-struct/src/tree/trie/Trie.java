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

    /**
     * 向字典树中添加单词(递归)
     * @param word
     */
    public void recAdd(String word){
        recAdd(word,root,0);
    }

    private void recAdd(String word, Node node, int index) {
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
        }
        recAdd(word, node.next.get(c), index + 1);
    }

    /**
     * 查找Tire中是否包含某个词
     * @param word
     * @return
     */
    public boolean contains(String word){
        Node cur = root;
        for(int i = 0 ; i < word.length(); i++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }
            cur = cur.next.get(c);
        }
        return cur.isWord;
    }

    public boolean isPrefix(String prefix){
        Node cur = root;
        for(int i = 0 ; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null){
                return false;
            }
            cur = cur.next.get(c);
        }
        return true;
    }

    public boolean search(String word){
        return match(word, root, 0);
    }

    private boolean match(String word, Node node, int index) {
        if(index >= word.length()){
            return node.isWord;
        }
        char c = word.charAt(index);
        if(c != '.'){
            if(node.next.get(c) == null){
                return false;
            }
            match(word, node.next.get(c), index + 1);
        }else {
            for(char key : node.next.keySet()){
              if(match(word, node.next.get(key), index + 1)){
                  return true;
              }
            }
        }
        return false;
    }

    public int getSize() {
        return size;
    }

}
