package leetcode.list;

import leetcode.list.util.ListNode;

public class InsertSort {

    public ListNode insertionSortList(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode dummy = new ListNode(-1);  //已排序的虚拟头结点
        dummy.next = head;
        ListNode cur = head.next;  //未排序的部分
        ListNode tail = head;   //已排序部分的尾节点

        while(cur != null) {
            ListNode next = cur.next;

            ListNode pre = dummy;
            ListNode node = pre.next;
            if(tail.val < cur.val) {
                tail = tail.next;
            }else {
                //找到需要插入前一个位置
                while (node != tail && node.val < cur.val) {
                    pre = pre.next;
                    node = node.next;
                }
                //从未排序的部分删除cur
                tail.next = next;
                //添加到已排序的部分
                cur.next = node;
                pre.next = cur;

                //更新tail位置
                if (tail.next == cur) tail = cur;
            }
            //更新cur位置
            cur = next;
        }
        return dummy.next;
    }

    public static void main(String[] args) {
        //[4,2,1,3];[-1,5,3,4,0];[3,2,4]
        int[] arr = new int[]{-1,5,3,4,0};
        ListNode head = ListNode.toList(arr);
        InsertSort sort = new InsertSort();
        ListNode cur = sort.insertionSortList(head);
        while(cur != null) {
            System.out.print(cur.val + "->");
            cur = cur.next;
        }
    }
}
