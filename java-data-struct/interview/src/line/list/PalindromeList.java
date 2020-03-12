package line.list;

import java.util.Stack;

public class PalindromeList {

    /**
     * 利用一个栈来存储传来的单链表，如果存入后出栈的顺序与传入的顺序一样，说明是回文链表
     * @param head  链表头结点
     * @return 是否是回文链表
     */
    public static boolean isPalindrome1(Node head) {
        if(head == null || head.next == null) return true;

        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while(cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        cur = head;
        while(!stack.isEmpty()) {
            Node n = stack.pop();
            if(n.v != cur.v) return false;
            cur = cur.next;
        }

        return true;
    }

    /**
     * 优化方法一
     * 只需要将链表的后半部分压入栈中，再与链表的前半部分对比，如果出现不一样的情况就说明不是回文链表
     * @param head
     * @return
     */
    public static boolean isPalindrome2(Node head) {
        if(head == null || head.next == null) return true;
        //找到后半部分开始的节点
        Node right = head;
        Node cur = head;
        if(cur.next.next == null) right = head.next;

        while(cur.next != null && cur.next.next != null) {
            right = right.next;
            cur = cur.next.next;
        }

        Stack<Node> stack = new Stack<>();
        while(right != null) {
            stack.push(right);
            right = right.next;
        }

        cur = head;
        while(!stack.isEmpty()) {
            Node n = stack.pop();
            if(n.v != cur.v) return false;
            cur = cur.next;
        }

        return true;
    }

    public static void main(String[] args) {
        Node head = new Node(1);
        head.next = new Node(2);
        System.out.println(isPalindrome2(head));
    }
}
