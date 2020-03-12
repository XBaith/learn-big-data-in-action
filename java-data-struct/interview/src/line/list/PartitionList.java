package line.list;

/**
 * 题目: <a>https://leetcode-cn.com/problems/partition-list-lcci/</a>
 */
public class PartitionList {

    /**
     * 只需要将链表分为小于x和大于等于x的两部分链表，之后再把分割好的两个链表相连
     * @param head
     * @param x
     * @return
     */
    public Node partition(Node head, int x) {
        Node shead = null;
        Node spos = null;
        Node bhead = null;
        Node bpos = null;
        Node cur = head;
        while(cur != null) {
            Node next = cur.next;
            cur.next = null;

            if(cur.v < x) { //小于x，放在左半边的链表中
                if(shead == null) {
                    shead = cur;
                    spos = cur;
                }else {
                    spos.next = cur;
                    spos = cur;
                }
            }else {  //大于等于x，放在右半边链表
                if(bhead == null) {
                    bhead = cur;
                    bpos = cur;
                }else {
                    bpos.next = cur;
                    bpos = cur;
                }
            }
            //准备判断下一个节点
            cur = next;
        }
        //合并两半部分链表
        //如果存在比x更小的节点
        if(shead != null) {
            spos.next = bhead;
        }
        return shead == null ? bhead : shead;
    }

}
