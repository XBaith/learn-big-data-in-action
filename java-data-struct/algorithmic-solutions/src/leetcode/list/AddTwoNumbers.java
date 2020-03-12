package leetcode.list;

import leetcode.list.util.ListNode;

import java.awt.*;

/**
 * <a>https://leetcode-cn.com/problems/add-two-numbers/</a>
 * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
 * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 *
 * 示例：
 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
 * 输出：7 -> 0 -> 8
 * 原因：342 + 465 = 807
 */
public class AddTwoNumbers {

    /**
     * 自己的方法对于数字很大的链表无法胜任。
     * 不能使用简单的余10处理，如果链表很长，即数字很大long类型也无法存下
     */
    public ListNode addTwoNumbersNotRight(ListNode l1, ListNode l2) {
        if(l1 == null || l2 == null) return null;

        long sum = 0L, sum1 = 0L;
        long cnt = 1L;
        ListNode cur = l1;
        while(cur != null) {
            sum += cur.val * cnt;
            cnt *= 10;
            cur = cur.next;
        }

        cur = l2;
        cnt = 1;
        while(cur != null) {
            sum1 += cur.val * cnt;
            cnt *= 10;
            cur = cur.next;
        }
        sum += sum1;

        ListNode res = null;
        ListNode last = null;
        //如果两者之和为0就直接返回0
        if(sum == 0) return new ListNode(0);

        while(sum != 0) {
            long d = sum % 10;
            if(res == null) {
                res = new ListNode((int)d);
                last = res;
            }else {
                ListNode node = new ListNode((int)d);
                last.next = node;
                last = node;
            }
            sum /= 10;
        }
        return res;
    }

    /**
     * 与上述方法不同的是，该方法将每个位数分别相加，并记录进位，将进位放在跟高一位与原两个数相加。
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dumpHead = new ListNode(0);
        ListNode cur1 = l1, cur2 = l2;
        int c = 0,sum = 0;
        int n1 = 0, n2 = 0;
        ListNode p = dumpHead;
        ListNode add = null;
        while(cur1 != null || cur2 != null) {
            n1 = cur1 == null ? 0 : cur1.val;
            n2 = cur2 == null ? 0 : cur2.val;
            sum = n1 + n2 + c;
            c = sum / 10;
            add = new ListNode(sum % 10);
            p.next = add;
            p = add;
            if(cur1 != null) cur1 = cur1.next;
            if(cur2 != null) cur2 = cur2.next;
        }
        if(c > 0) p.next = new ListNode(c);

        return dumpHead.next;
    }

    public static void main(String[] args) {
        ListNode l1 = new ListNode(9);
        ListNode l2 = new ListNode(1);
        ListNode cur = l2;
        for(int i = 0; i < 9; i++) {
            ListNode node = new ListNode(9);
            cur.next = node;
            cur = node;
        }

        AddTwoNumbers addTwo = new AddTwoNumbers();
        ListNode head = addTwo.addTwoNumbers(l1, l2);
        while(head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
    }
}
