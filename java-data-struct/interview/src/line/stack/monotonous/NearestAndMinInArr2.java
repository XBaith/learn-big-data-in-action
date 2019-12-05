package line.stack.monotonous;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 算出一个<b>有重复元素</b>的数组中，x位置左右两侧离它最近且小于其值的索引
 */
public class NearestAndMinInArr2 {
    public int[][] nearestAndMin(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<List<Integer>> stack = new Stack<>();
        for(int i = 0; i < arr.length; i++) {
            while(!stack.isEmpty() && arr[i] < arr[stack.peek().get(0)]) {
                List<Integer> list = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
                for(Integer index : list) {
                    res[index][0] = left;
                    res[index][1] = i;
                }
            }
            if(!stack.isEmpty() && arr[i] == arr[stack.peek().get(0)])
                stack.peek().add(i);
            else {  //新遍历的值大于单调栈顶的值，或者单调栈为空
                List<Integer> newList = new ArrayList<>();
                newList.add(i);
                stack.push(newList);
            }
        }
        //剩下的都是符合单调栈结构，但是没有清算左右值的
        while(!stack.isEmpty()) {
            List<Integer> list = stack.pop();
            int left = stack.isEmpty() ? -1 : stack.peek().get(stack.peek().size() - 1);
            for(Integer index : list) {
                res[index][0] = left;
                res[index][1] = -1;
            }
        }

        return res;
    }

    public static void main(String[] args) {
        NearestAndMinInArr2 arr2 = new NearestAndMinInArr2();
        int[] arr = {3,1,3,4,3,5,3,2,2};
        int[][] res = arr2.nearestAndMin(arr);
        for(int i = 0; i < res.length; i++) {
            int left = res[i][0], right = res[i][1];
            System.out.println(i + " : [" + left + ", " + right + "]");
        }
    }
}
