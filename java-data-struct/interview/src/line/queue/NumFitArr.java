package line.queue;

import java.util.LinkedList;

/**
 * 找到在一个数组中满足max(arr[i...j]) - min(arr[i...j]) <= num的数组总数
 */
public class NumFitArr {

    public int fitNum(int[] arr, int num) {
        if(arr == null || arr.length == 0 || num > arr.length)
            return 0;

        int i, j, res = 0;
        LinkedList<Integer> maxq = new LinkedList<>();
        LinkedList<Integer> minq = new LinkedList<>();
        for(i = 0; i < arr.length; i++) {
            for(j = 0; j < arr.length; j++) {
                if(minq.isEmpty() || minq.peekLast() != j) {
                    while (!minq.isEmpty() && arr[minq.peekLast()] >= arr[j])
                        minq.pollLast();
                    minq.addLast(j);
                    while (!maxq.isEmpty() && arr[maxq.peekLast()] <= arr[j])
                        maxq.pollLast();
                    maxq.addLast(j);
                }
                if(maxq.peekFirst() - minq.peekFirst() > num) break;
            }
            res += j - i;
            if(minq.peekFirst() <= i)
                minq.pollFirst();
            if(maxq.peekFirst() <= i)
                maxq.pollFirst();
        }
        return res;
    }

}
