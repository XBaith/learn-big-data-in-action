package line.queue;

import java.util.LinkedList;

public class WindowMaxItem {
    /**
     * 双端队列maxq用于保存部分数组元素的索引
     * 如果maxq为空，直接将索引放入双端队列；
     * 如果maxq不为空：假设从队尾取出的元素索引为j，遍历的当前元素索引为i
     * 1)arr[j] > arr[i]，将i放入队列
     * 2)arr[j] <= arr[i]，将j弹出队列
     * 如果maxq的队首元素f = i - w，说明队首元素的索引值等于当前遍历元素的索引值 - 窗口大小，即队首的元素不包含在滑动窗口之中，应当弹出
     * @param arr
     * @param window
     * @return
     */
    private int[] getWindowMaxItem(int[] arr, int window) {
        if(arr == null  || window < 1 || arr.length < window)
            return null;

        int[] res = new int[arr.length - window + 1];
        LinkedList<Integer> maxq = new LinkedList<>();
        int index = 0;
        for(int i = 0; i < arr.length; i++) {
            while(!maxq.isEmpty() && arr[maxq.peekLast()]  <= arr[i]){
                maxq.pollLast();
            }
            maxq.addLast(i);
            if(maxq.peekFirst() == i - window)
                maxq.pollFirst();
            if(i >= window - 1)
                res[index++] = arr[maxq.peekFirst()];
        }

        return res;
    }

    public static void main(String[] args) {
        WindowMaxItem maxItem = new WindowMaxItem();
        int[] arr = {4,3,5,4,3,3,6,7};
        for(int n : maxItem.getWindowMaxItem(arr, 3))
            System.out.print(n + " ");
    }
}
