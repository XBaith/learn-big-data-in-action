package leetcode.list;

import leetcode.list.util.ListNode;

import java.util.List;

/**
 * <a>https://leetcode-cn.com/problems/sort-list/</a>
 * Sort a linked list in O(n log n) time using constant space complexity.
 * Example 1:
 * Input: 4->2->1->3
 * Output: 1->2->3->4
 * Example 2:
 * Input: -1->5->3->4->0
 * Output: -1->0->3->4->5
 */
public class SortList {

    /**
     * 题目要求的空间复杂度为O(NlogN)，因此需要用到归并排序或者快速排序
     * 又因为要求O(1)的空间复杂度，所以不能用普通递归的方式，应该用bottom-up，即自底向上的归并
     * 需要不断地分割和合并
     * @param head
     * @return
     */
    public ListNode mySortList(ListNode head) {
        if(head == null || head.next == null) return head;

        int length = 0; //链表的长度
        ListNode cur = head;
        while(cur != null) {
            length++;
            cur = cur.next;
        }

        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode tail = dummy;

        int step;   //每次归并的节点个数
        for(step = 1; step < length; step <<= 1) {
            cur = dummy.next;
            tail = dummy;
            while(cur != null) {
                ListNode left = cur;
                ListNode right = split(cur, step);
                cur = split(right, step);

                //将归并过得链表挂接
                tail.next = merge(left, right);
                //更新tail的位置
                while(tail.next != null) {
                    tail = tail.next;
                }
            }
        }
        return dummy.next;
    }

    /**
     * 将链表的前step个链表切分，之后返回切分后的右半部分
     * @param head
     * @param step
     * @return
     */
    private ListNode split(ListNode head, int step) {
        ListNode pre = head;
        while(--step != 0 && pre != null) {
            pre = pre.next;
        }

        if(pre == null) return null;

        ListNode right = pre.next;
        pre.next = null;
        return right;
    }

    private ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(-1);
        ListNode lPos = left, rPos = right;
        ListNode pre = dummy;
        while(lPos != null && rPos != null) {
            if(lPos.val < rPos.val) {
                //链入排序链表
                pre.next = lPos;
                //更新指针位置
                lPos = lPos.next;
                pre = pre.next;
            }else {
                pre.next = rPos;
                rPos = rPos.next;
                pre = pre.next;
            }
        }
        //链入剩余的节点
        if(lPos == null) {
            pre.next = rPos;
        }else {
            pre.next = lPos;
        }
        return dummy.next;
    }

    /**
     * 快慢指针确定链表的中点
     * @param head
     * @return
     */
    public ListNode getMid(ListNode head) {
        //1.先找到链表的中间节点
        ListNode pre = head;
        ListNode next = head.next.next;
        while(next.next != null && next.next.next!= null) {
            pre = pre.next;
            next = next.next.next;
        }
        return pre.next;
    }

    /**
     * 同样是自底向上的归并，在局部做了优化的方法
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        // 增加头结点
        ListNode pre = new ListNode(0);
        pre.next = head;
        ListNode preNode = pre;

        /*循环增加排序子链表的长度
        (preNode = sortSubList(preNode, len)) != null 作为边界条件，
            意为第一次调用sortSubList就返回null，说明链表长度小于len*2，可以结束排序
        */
        for (int len = 1; (preNode = sortSubList(preNode, len)) != null; len *= 2){
            while (preNode != null){
                preNode = sortSubList(preNode, len);
            }
            preNode = pre;
        }
        return pre.next;
    }

    /**
     * 返回排序后的链表的最后一个节点
     */
    ListNode sortSubList(ListNode pre, int num){
        //当前指针位置
        int fstnum = 0, lstnum = 0;
        //当前节点
        ListNode fst = pre.next, lst = fst;

        //找到lst节点位置
        for (int i = 0; i < num; i++){
            //fst长度不够，直接返回null，此时子链表已经是排好序的
            if (lst == null)
                return null;
            lst = lst.next;
        }
        //比较，终止条件为fstnum或lastnum大于子链表长度，或者lsatnum到达链表末尾
        while (fstnum < num && lstnum < num && lst != null){
            if (fst.val <= lst.val){
                pre.next = fst;
                fst = fst.next;
                fstnum++;
            }
            else{
                pre.next = lst;
                lst = lst.next;
                lstnum++;
            }
            pre = pre.next;
        }
        //如果fst子链表还有剩余节点，接到当前节点后面
        if (fstnum < num){
            pre.next = fst;
            //让当前节点走到子链表末尾
            for (; fstnum < num; fstnum++)
                pre = pre.next;
            //接上后面的链表
            pre.next = lst;
        }
        //和上面类似，此时还需考虑last子链表长度小于num的情况
        if (lstnum < num && lst != null){
            pre.next = lst;
            for (; lstnum < num && pre != null; lstnum++)
                pre = pre.next;
        }
        //返回最后一个节点，作为下次排序的pre
        return pre;
    }

    public static void main(String[] args) {
        //[4,2,1,3];[-1,5,3,4,0]
        int[] arr = new int[]{-1,5,3,4,0};
        ListNode head = ListNode.toList(arr);
        SortList sort = new SortList();

        ListNode.result(sort.mySortList(head));
    }

}
