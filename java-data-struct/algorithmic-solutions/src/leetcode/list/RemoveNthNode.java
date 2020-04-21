package leetcode.list;

import leetcode.list.util.ListNode;

/**
 * <a>https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/</a>
 * 给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
 *
 * 示例：
 * 给定一个链表: 1->2->3->4->5, 和 n = 2.
 * 当删除了倒数第二个节点后，链表变为 1->2->3->5.
 *
 * 说明：给定的 n 保证是有效的。
 * 进阶：你能尝试使用一趟扫描实现吗？
 */
public class RemoveNthNode {

    public ListNode myRemoveNthFromEnd(ListNode head, int n) {
        if(head.next == null || head == null) return null;

        //反转之后删除正数的第n个节点，之后再次反转
        ListNode pre = null;
        ListNode next = null;
        ListNode cur = head;
        while(cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        //找到需要删除的节点的前继
        ListNode dummy = new ListNode(-1);
        dummy.next = pre;
        pre = dummy;
        while(--n != 0 && pre != null) {
            pre = pre.next;
        }

        if(n != 0) return null;

        //删除第n个节点
        next = pre.next.next == null ? null : pre.next.next;
        pre.next = next;

        //再次反转，恢复顺序
        cur = dummy.next;
        pre = null;
        while(cur != null) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }

        return pre;
    }

    /**
     * 一次扫描的方法：
     * 第一个指针从列表的开头向前移动 n+1n+1 步，而第二个指针将从列表的开头出发。
     * 现在，这两个指针被 nn 个结点分开。我们通过同时移动两个指针向前来保持这个恒定的间隔，
     * 直到第一个指针到达最后一个结点。此时第二个指针将指向从最后一个结点数起的第 nn 个结点。
     * 我们重新链接第二个指针所引用的结点的 next 指针指向该结点的下下个结点。
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        // Advances first pointer so that the gap between first and second is n nodes apart
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }
        // Move first to the end, maintaining the gap
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        RemoveNthNode removeNthNode = new RemoveNthNode();
        int[] arr = new int[]{1,2,3};
        ListNode head = removeNthNode.myRemoveNthFromEnd(ListNode.toList(arr), 3);

        ListNode.result(head);
    }
}
