package leetcode.list.util;

import scala.reflect.internal.pickling.UnPickler;

import java.util.Scanner;

/**
 * https://leetcode-cn.com/problems/linked-list-cycle/
 * 给定一个链表，判断链表中是否有环。
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 *
 * 示例 1：
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 */
public class CycleList {

    /**
     * 用快慢指针
     * 如果快指针为null说明链表一定存在环，否则快指针迟早到达满指针的位置，那么就说明存在环
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if(head == null || head.next == null) return false;
        ListNode cur = head;
        ListNode fast = head.next;

        while(cur != fast) {
            cur = cur.next;
            fast = fast.next == null ? null : fast.next.next;
            if(fast == null) return false;
        }

        return true;
    }

    public static void main(String[] args) {

    }

}
