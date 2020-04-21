package leetcode.list;

import leetcode.list.util.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle-ii/
 * 给定一个链表，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 *
 * 说明：不允许修改给定的链表。
 * 示例 1：
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：tail connects to node index 1
 * 解释：链表中有一个环，其尾部连接到第二个节点
 */
public class CycleListII {
    /**
     * 题解：https://leetcode-cn.com/problems/linked-list-cycle-ii/solution/huan-xing-lian-biao-ii-by-leetcode/
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if(head == null) return null;

        ListNode slow = head, fast = head;
        do {
            if(fast.next == null || fast.next.next == null) return null;
            slow = slow.next;
            fast = fast.next.next;
        } while(slow != fast);

        ListNode cur = head;
        while(cur != slow) {
            cur = cur.next;
            slow = slow.next;
        }

        return cur;
    }

    public ListNode myDetectCycle(ListNode head) {
        if(head == null || head.next == null) return null;

        Set<ListNode> set = new HashSet<>();
        ListNode cur = head;
        while(cur != null) {
            if(set.contains(cur)) return cur;
            else {
                set.add(cur);
            }
            cur = cur.next;
        }
        return null;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{3,2,0,-4};
        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        head.next.next = new ListNode(0);
        head.next.next.next = new ListNode(-4);
        head.next.next.next.next = head.next;

        CycleListII cycle = new CycleListII();

        cycle.detectCycle(head);
    }

}
