package graph.ai;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/shortest-path-in-binary-matrix/
 */
public class Solution1091 {
    private int[][] grid;
    private boolean[][] visited;
    private int R, C;
    //八连通
    private int[][] dirs = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    private int[] dis;

    public int shortestPathBinaryMatrix(int[][] grid) {
        this.grid = grid;
        R = grid.length;
        C = grid[0].length;

        if(grid[0][0] == 1 || grid[R-1][C-1] == 1)return -1;
        if(R == 0 && C == 0) return 1;

        dis = new int[R * C];

        visited = new boolean[R][C];

        return bfs(0, 0);
    }

    private int bfs(int x, int y) {
        Queue<Integer> queue = new LinkedList<>();
        int v = x * R + y;
        dis[v] = 1;
        queue.add(v);
        visited[x][y] = true;

        while(!queue.isEmpty()){
            int vertex = queue.poll();

            int curx = vertex / C, cury = vertex % C;
            if(curx == R -1 && cury == C - 1)
                break;

            for(int d = 0; d < 8; d++){
                int nextx = curx + dirs[d][0];
                int nexty = cury + dirs[d][1];

                if(nextx >= 0 && nextx < R && nexty >= 0 && nexty < C) {
                    if (!visited[nextx][nexty] && grid[nextx][nexty] == 0) {
                        visited[nextx][nexty] = true;
                        int w = nextx * R + nexty;
                        queue.add(w);
                        dis[w] = dis[vertex] + 1;
                    }
                }
            }
        }

        return dis[R * C - 1];
    }

    public static void main(String[] args) {
        int[][] grid = {{0,1,0,1,0},
                        {1,0,0,0,1},
                        {0,0,1,1,1},
                        {0,0,0,0,0},
                        {1,0,1,0,0}};
//        int[][] grid = {{0,0},{0,0}};
        Solution1091 solution1091 = new Solution1091();
        System.out.println(solution1091.shortestPathBinaryMatrix(grid));
    }
}
