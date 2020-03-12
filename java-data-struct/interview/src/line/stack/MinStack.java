package line.stack;

import java.util.Stack;

public class MinStack {
    private Stack<Integer> stack;
    private Stack<Integer> minStack;
    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int x) {
        if(stack.isEmpty()) {
            stack.push(x);
            minStack.push(x);
            return;
        }
        if(x <= minStack.peek()) {
            minStack.push(x);
        }else {
            minStack.push(minStack.peek());
        }
        stack.push(x);
    }

    public void pop() {
        if(stack.isEmpty())
            return;
        minStack.pop();
        stack.pop();
    }

    public int top() {
        if(stack.isEmpty())
            return 0;
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }
}
