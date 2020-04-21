package leetcode.list;

import leetcode.list.util.ListNode;

import java.util.List;

/**
 * <a>https://leetcode-cn.com/problems/merge-two-sorted-lists/</a>
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 *
 * 示例：
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 */
public class MergeTwoSortedLists {

    /**
     * 自己写的代码不够精简，虽然思路一致
     * @param l1
     * @param l2
     * @return
     */
    public ListNode myMergeTwoLists(ListNode l1, ListNode l2) {
        if(l1 == null && l2 == null) return null;
        else if(l1 == null) return l2;
        else if(l2 == null) return l1;

        ListNode resNode = new ListNode(0);
        ListNode head = resNode;
        while(l1 != null || l2 != null) {
            if (l1 == null) {
                resNode.next = l2;
                break;
            }else if(l2 == null) {
                resNode.next = l1;
                break;
            }
            int n1 = l1.val;
            int n2 = l2.val;
            if(n1 < n2) {
                resNode.next = new ListNode(n1);
                l1 = l1.next;
            }
            else if(n1 > n2){
                resNode.next = new ListNode(n2);
                l2 = l2.next;
            }else {
                ListNode add = new ListNode(n1);
                resNode.next = add;
                add.next = new ListNode(n2);
                l1 = l1.next;
                l2 = l2.next;
            }
            if(resNode.next.next != null) resNode = resNode.next.next;
            else resNode = resNode.next;
        }
        return head.next;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode preHead = new ListNode(-1);
        ListNode pre = preHead;

        while(l1 != null && l2 != null) {
            if(l1.val <= l2.val) {
                pre.next = l1;
                l1 = l1.next;
            }else {
                pre.next = l2;
                l2 = l2.next;
            }
            pre = pre.next;
        }
        //把未遍历比较完的链表进行链接
        pre.next = l1 == null ? l2 : l1;
        return preHead.next;
    }

    /**
     * 递归的方法，虽然代码很简单，但是占用的空间复杂度比前两个要大:O(N + M)
     * @param l1
     * @param l2
     * @return
     */
    public ListNode recMergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        else if (l2 == null) {
            return l1;
        }
        else if (l1.val < l2.val) {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
        else {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }

    }

    public static void main(String[] args) {
        MergeTwoSortedLists merge = new MergeTwoSortedLists();
        ListNode l1 = new ListNode(2);
        ListNode l2 = new ListNode(1);

        ListNode head = merge.mergeTwoLists(l1, l2);
        while(head != null) {
            System.out.print(head.val + "->");
            head = head.next;
        }
    }
}
