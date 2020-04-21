package leetcode.list;

import leetcode.list.util.ListNode;

public class SelectSortList {

    public ListNode selectSort(ListNode head) {
        ListNode small, smallPre;
        ListNode cur = head;    //未排序部分的头结点
        ListNode tail = null;   //已经排序的尾节点

        while(cur != null) {
            small = cur;
            smallPre = getSmallPre(cur);
            //删除未排序部分的最小节点，并得到最小节点
            if(smallPre != null) {
                small = smallPre.next;
                smallPre.next = small.next;
            }
            //将在未排序部分得到的最小节点放在排序部分尾部
            if(tail == null) {
                head = small;   //链表中的最小节点
            }else {
                tail.next = small;
            }
            tail = small;
            cur = cur == small ? cur.next : cur;    //small不属于未排序部分，那么cur指向下一个节点
        }

        return head;
    }

    public ListNode getSmallPre(ListNode head) {
        ListNode smallPre = null;
        ListNode small = head;
        ListNode pre = head;
        ListNode cur = head.next;
        while(cur != null) {
            if(small.val > cur.val) {
                small = cur;
                smallPre = pre;
            }
            pre = cur;
            cur = cur.next;
        }
        return smallPre;
    }

}
