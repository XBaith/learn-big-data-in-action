package line.stack.monotonous;

import java.util.Stack;

/**
 * 单调栈结构
 * 算出一个<b>没有重复元素</b>的数组中，x位置左右两侧离它最近且小于其值的索引
 * 借助于单调递减栈（自顶向底递减），可以达到O(n)
 */
public class NearestAndMinInArr1 {
    public int[][] nearestAndMin(int[] arr) {
        int[][] res = new int[arr.length][2];
        Stack<Integer> monoS = new Stack<>();
        for(int i = 0; i < arr.length; i++){
            //如果栈顶元素大于遍历到的值，不满足单调递减栈的原则
            while(!monoS.isEmpty() && arr[monoS.peek()] > arr[i]){
                int x = monoS.pop();
                //如果单调栈为空，左边就不存在最近且小于x的索引；否则就是x下一个栈帧的索引
                int left = monoS.isEmpty() ? -1 : monoS.peek();
                res[x][0] = left;
                res[x][1] = i;
            }
            monoS.push(i);
        }
        //处理符合单调栈的，直接压入的索引，因为是直接压入的，所以x的值一直小于新遍历得到的值，因此x的右侧应该不存在想要的值，即-1
        while(!monoS.isEmpty()){
            int x = monoS.pop();
            //如果单调栈为空，左边就不存在最近且小于x的索引；否则就是x下一个栈帧的索引
            int left = monoS.isEmpty() ? -1 : monoS.peek();
            res[x][0] = left;
            res[x][1] = -1;
        }
        return res;
    }

    public static void main(String[] args) {
        NearestAndMinInArr1 nNmA = new NearestAndMinInArr1();
        int[] arr = {3,4,1,5,6,2,7};
        int[][] res = nNmA.nearestAndMin(arr);
        for(int i = 0; i < res.length; i++) {
            int left = res[i][0], right = res[i][1];
            System.out.println(i + " : [" + left + ", " + right + "]");
        }
    }
}
