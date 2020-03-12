package line.list;

import java.util.HashMap;

public class CopyListWithRandPoint {

    private static class Node {
        int value;
        Node next;
        Node rand;  //随机指针
        public Node(int v) {
            value = v;
        }
    }

    public Node copy(Node head) {
        HashMap<Node, Node> map = new HashMap<>();
        //先构造源节点和副本节点之间的映射
        Node cur = head;
        while(cur != null) {
            map.put(cur, new Node(cur.value));
            cur = cur.next;
        }
        //再复制节点的指针
        cur = head;
        while(cur != null) {
            map.get(cur).next = map.get(cur.next);
            map.get(cur).rand = map.get(cur.rand);
            cur = cur.next;
        }
        return map.get(head);
    }

    public Node copyWithVars(Node head) {
        if(head == null) return head;
        //复制副本
        Node cur = head;
        Node copyNode = null;
        Node next = null;
        while(cur != null) {
            copyNode = new Node(cur.value);
            copyNode.next = cur.next;
            cur.next = copyNode;
            cur = copyNode.next;
        }
        //复制指针
        cur = head;
        copyNode = null;
        while(cur != null) {
            next = cur.next.next;
            copyNode = cur.next;
            copyNode.rand = cur.rand != null ? cur.rand.next : null;
            cur = next;
        }
        //拆分链表
        cur = head;
        Node res = head.next;
        while(cur != null) {
            next = cur.next.next;
            copyNode = cur.next;
            cur.next = copyNode.next;
            copyNode.next = next != null ? next.next : null;
            cur = next;
        }

        return res;
    }

    public static void main(String[] args) {

    }
}
