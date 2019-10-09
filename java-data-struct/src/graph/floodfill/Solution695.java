package graph.floodfill;

/**
 * https://leetcode-cn.com/problems/max-area-of-island/
 */
public class Solution695 {

    private static final int[][] dirs = {{-1,0},{0,1},{1,0},{0,-1}};

    private int[][] grid;
    private boolean[][] visited;

    public int maxAreaOfIsland(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0)
            return 0;

        visited = new boolean[grid.length][grid[0].length];
        this.grid = grid;

        int area = 0;
        int max = 0;

        for(int x = 0; x < grid.length; x++){
            for(int y = 0; y < grid[x].length; y++){
                if(grid[x][y] == 1 && !visited[x][y]){
                    area = dfs(x, y, area);
                    if(max < area){
                        max = area;
                    }
                    area = 0;
                }
            }
        }

        return max;
    }

    private int dfs(int x, int y, int area) {
        visited[x][y] = true;
        area++;

        for(int d = 0; d < 4; d++){
            int nextx = x + dirs[d][0];
            int nexty = y + dirs[d][1];

            if( isInArea(nextx, nexty)) {
                if (!visited[nextx][nexty] && grid[nextx][nexty] == 1) {
                    area = dfs(nextx, nexty, area);
                }
            }
        }

        return area;
    }

    private boolean isInArea(int x, int y) {
        return x >= 0 && x <= grid.length - 1 && y >= 0 && y <= grid[0].length - 1;
    }

    public static void main(String[] args) {
        int[][] grid = {{1,1,0,1,1},
                        {1,0,0,0,0},
                        {0,0,0,0,1},
                        {1,1,0,1,1}};
        Solution695 solution695 = new Solution695();
        System.out.println(solution695.maxAreaOfIsland(grid));
    }

}
