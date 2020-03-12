package line.list;

public class ReverseList {
    private static class Node {
        int v;
        Node next;
        Node last;
        public Node(int v) {
            this.v = v;
        }
    }

    /**
     * 反转单向链表
     * @param head
     * @return
     */
    public static Node reverse(Node head) {
        Node pre = null;
        Node next = null;
        while(head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    /**
     * 反转双向链表
     * @param head
     * @return
     */
    public static Node reverseDouble(Node head) {
        Node pre = null, next = null;
        while(head != null) {
            next = head.next;
            head.next = pre;
            head.last = next;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        //Node head = new Node(-1);
        Node node1 = new Node(1);
        //head.next = node1;

        Node node2 = new Node(2);
        node1.next = node2;

        Node node3 = new Node(3);
        node2.next = node3;

        Node node4 = new Node(4);
        node3.next = node4;

        node1 = reverse(node1);

        Node cur = node1;
        while(cur != null) {
            if(cur.next != null)
                System.out.print(cur.v + "->");
            else System.out.print(cur.v);
            cur = cur.next;
        }
    }
}
