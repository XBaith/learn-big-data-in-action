package leetcode.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <a>https://leetcode-cn.com/problems/dui-lie-de-zui-da-zhi-lcof/</a>
 * 请定义一个队列并实现函数 max_value 得到队列里的最大值，要求函数max_value、push_back 和 pop_front 的均摊时间复杂度都是O(1)。
 * 若队列为空，pop_front 和 max_value 需要返回 -1
 *
 * 示例 1：
 * 输入:
 * ["MaxQueue","push_back","push_back","max_value","pop_front","max_value"]
 * [[],[1],[2],[],[],[]]
 * 输出: [null,null,null,2,1,2]
 *
 * 示例 2：
 * 输入:
 * ["MaxQueue","pop_front","max_value"]
 * [[],[],[]]
 * 输出: [null,-1,-1]
 *  
 * 限制：
 * 1 <= push_back,pop_front,max_value的总操作数 <= 10000
 * 1 <= value <= 10^5
 */
public class MaxQueue {

    private Queue<Integer> queue;
    private LinkedList<Integer> maxQ;

    public MaxQueue() {
        queue = new LinkedList<>();
        maxQ = new LinkedList<>();
    }

    public int max_value() {
        if(maxQ.isEmpty()) return -1;
        return maxQ.getFirst();
    }

    public void push_back(int value) {
        queue.add(value);
        if(maxQ.isEmpty()) {
            maxQ.add(value);
            return;
        }
        while(!maxQ.isEmpty() && maxQ.getLast() < value) {
            maxQ.removeLast();
        }
        maxQ.add(value);
    }

    public int pop_front() {
        if(queue.isEmpty()) return -1;
        int res = queue.poll();
        if(res == maxQ.getFirst()) {
            maxQ.removeFirst();
        }
        return res;
    }

    public static void main(String[] args) {
        MaxQueue obj = new MaxQueue();
        obj.push_back(1);
        obj.push_back(2);
        System.out.println(obj.max_value());
        System.out.println(obj.pop_front());
        System.out.println(obj.max_value());
    }
}
/**
 * 时间复杂度：O(1)O(1)（插入，删除，求最大值）
 * 删除操作于求最大值操作显然只需要 O(1)O(1) 的时间。
 * 而插入操作虽然看起来有循环，做一个插入操作时最多可能会有 nn 次出队操作。但要注意，由于每个数字只会出队一次，因此对于所有的 nn 个数字的插入过程，对应的所有出队操作也不会大于 nn 次。因此将出队的时间均摊到每个插入操作上，时间复杂度为 O(1)O(1)。
 * 空间复杂度：O(n)O(n)，需要用队列存储所有插入的元素。
 */
