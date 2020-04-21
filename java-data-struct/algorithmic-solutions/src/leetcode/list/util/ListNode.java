package leetcode.list.util;

public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int val) { this.val = val; }

    public static ListNode toList(int[] arr) {
        ListNode pre = new ListNode(arr[0]);
        ListNode head = pre;
        for(int i = 1; i < arr.length; i++) {
            pre.next = new ListNode(arr[i]);
            pre = pre.next;
        }
        return head;
    }

    public static void result(ListNode head) {
        ListNode cur = head;
        while(cur != null) {
            if(cur.next != null)
                System.out.print(cur.val + "->");
            else
                System.out.print(cur.val);
            cur = cur.next;
        }
    }
}
