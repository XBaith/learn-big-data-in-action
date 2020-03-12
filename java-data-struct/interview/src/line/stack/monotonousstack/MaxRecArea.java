package line.stack.monotonousstack;

import java.util.Stack;

/**
 * 从一个二维矩阵中找出连续的最大矩阵面积
 * 例如map=[1,0,1,1
 *         1,1,1,1
 *         1,1,1,0]
 * 上个例子中的最大连续的矩阵面积就是6
 * 解决思路就是先得出从底部到顶部连续为1的个数，例如最后一行从底向上的连续为1的个数分别为height:[3,2,3,0]，其只要该行某列为0那么他就不存在连续为1
 * 那么可以将height的数值当做一个柱形图，接下来就分别计算每列的左右两边的最近且小于它的位置，
 * 两个位置之差（长） ×　height（高）　＝　当前最大的矩阵面积
 *
 * 因此需要用到单调栈来计算最近且小于的位置，每列进栈出栈一次，每行都需要遍历，因此时间复杂度就是O(NM)，其中N是行，M是。
 */
public class MaxRecArea {

    public int maxRecArea(int[][] map) {
        if(map.length == 0 || map == null)
             return 0;

        int maxArea = 0;
        int[] height = new int[map[0].length];
        for(int n = 0; n < map.length; n++) {
            for(int m = 0; m < height.length; m++) {
                height[m] = map[n][m] == 0 ? 0 : height[m] + 1;
            }
            maxArea = Math.max(maxRecAreaFromBottom(height), maxArea);
        }

        return maxArea;
    }

    private int maxRecAreaFromBottom(int[] height) {
        if(height.length == 0)
            return 0;

        int maxArea = 0;
        //单调栈
        Stack<Integer> monoStack = new Stack<>();
        for(int i = 0; i < height.length; i++) {
            while(!monoStack.isEmpty() && height[i] < height[monoStack.peek()]) {
                int j = monoStack.pop();
                int left = monoStack.isEmpty() ? -1 : monoStack.peek();
                int curRecArea = (i - (left + 1)) * height[j];
                maxArea = Math.max(curRecArea, maxArea);
            }
            monoStack.push(i);
        }
        while(!monoStack.isEmpty()) {
            int j = monoStack.pop();
            int left = monoStack.isEmpty() ? -1 : monoStack.peek();
            int curRecArea = (height.length - (left + 1)) * height[j];
            maxArea = Math.max(curRecArea, maxArea);
        }

        return maxArea;
    }

    public static void main(String[] args) {
        MaxRecArea maxRecArea = new MaxRecArea();
        int[][] map = { {1,0,1,1},
                        {1,1,1,1},
                        {1,1,1,0} };
        System.out.println(maxRecArea.maxRecArea(map));

        int[] height = {3,4,5,4,3,6};
        System.out.println(maxRecArea.maxRecAreaFromBottom(height));
    }

}
