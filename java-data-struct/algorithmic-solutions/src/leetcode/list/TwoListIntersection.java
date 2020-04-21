package leetcode.list;

import leetcode.list.util.ListNode;

/**
 * <a>https://leetcode-cn.com/problems/intersection-of-two-linked-lists/</a>
 * 编写一个程序，找到两个单链表相交的起始节点。
 */
public class TwoListIntersection {

    /**
     * 用两个指针分别遍历LA和LB,当某一个指针先到达链尾时，就跳转到另一个链首开始继续遍历，直到两个到达相同的位置。
     * 自己的方法不利索，我真是个废物
     * @param headA
     * @param headB
     * @return
     */
    public ListNode myGetIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;

        ListNode pa = headA, pb = headB;
        ListNode lastA = null, lastB = null;
        while(pa != pb) {
            if(lastA != null && lastB != null && lastA != lastB)    return null;
            if(pa.next == null && pb.next == null) return pa == pb ? pa : null;
            if(pa.next == null) {
                lastA = pa;
                pa = headB;
                pb = pb.next;
            }else if(pb.next == null) {
                lastB = pb;
                pb = headA;
                pa = pa.next;
            }else {
                pa = pa.next;
                pb = pb.next;
            }

        }

        return pa;
    }

    /**
     * 题解下的同学写的，但是这个方法如果是三目运算符就会非常耗时，修改为if else语句就会提高从执行耗时击败2%提高到100%的用户
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null) return null;
        ListNode pa = headA, pb = headB;
        while(pa != pb) {
            if(pa == null) pa = headB; else pa = pa.next;
            if(pb == null) pb = headA; else pb = pb.next;
        }
        return pa;
    }

    public static void main(String[] args) {
        ListNode ha = new ListNode(1);
        ListNode hb = new ListNode(2);

        TwoListIntersection two = new TwoListIntersection();
        two.getIntersectionNode(ha, hb);
    }
}
