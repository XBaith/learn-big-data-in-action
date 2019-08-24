package tree.trie;


import java.util.TreeMap;

public class MapSum {
    private class Node {
        int value;
        TreeMap<Character, Node> next;

        Node(int value) {
            this.value = value;
            next = new TreeMap<>();
        }

        Node() {
            this(0);
        }
    }

    private Node root;

    /** Initialize your data structure here. */
    public MapSum() {
        root = new Node();
    }

    public void insert(String key, int val) {
        Node cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (cur.next.get(c) == null) {
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }

        cur.value = val;
    }

    public int sum(String prefix) {
        Node cur = root;
        for(int i = 0 ; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null){
                return 0;
            }
            cur = cur.next.get(c);
        }
        return nodeSum(cur);
    }

    private int nodeSum(Node cur) {
        int val = cur.value;
        for(char c : cur.next.keySet()){
            val += nodeSum(cur.next.get(c));
        }
        return val;
    }
}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */

