package line.list;

public class RemoveMid {

    public static Node removeMid(Node head) {
        if(head == null || head.next == null) {
            return head;
        }
        if(head.next.next == null) {
            return head.next;
        }

        Node pre = head;
        Node cur = head.next.next;
        while(cur.next != null && cur.next.next != null) {
            pre = pre.next;
            cur = cur.next.next;
        }
        pre.next = pre.next.next;   //删除节点

        return head;
    }

    /**
     * a/b = 0 不删除任何节点
     * a/b > 1 不删除任何节点
     * 比如1->2->3->4->5 r = a/b
     * r落在
     * (0,1/5]，删除1
     * (1/5,2/5]，删除2，以此类推
     * @param head
     * @param a
     * @param b
     * @return
     */
    public Node removeAB(Node head, int a, int b) {
        if(a < 1 || a > b) {
            return head;
        }
        //先统计节点数
        int n = 0;
        Node cur = head;
        while(cur != null) {
            n++;
            cur = cur.next;
        }
        int remove = (int) Math.ceil(a * n / b);

        if(remove == 1) return head.next;

        Node pre = head;
        while(--remove != 1) {
            pre = pre.next;
        }
        pre = pre.next.next;
        return head;
    }

    public static void main(String[] args) {
        Node head = new Node(-1);
        Node node1 = new Node(1);
        head.next = node1;

        Node node2 = new Node(2);
        node1.next = node2;

        Node node3 = new Node(3);
        node2.next = node3;

        Node node4 = new Node(4);
        node3.next = node4;

        head = removeMid(head);

        Node cur = head.next;
        while(cur != null) {
            if(cur.next != null)
                System.out.print(cur.v + "->");
            else System.out.print(cur.v);
            cur = cur.next;
        }
    }
}
