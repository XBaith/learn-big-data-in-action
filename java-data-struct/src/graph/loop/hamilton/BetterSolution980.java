package graph.loop.hamilton;

class BetterSolution980 {
    private int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private int R, C;
    private int[][] grid;
    private int start, end;

    public int uniquePathsIII(int[][] grid) {

        this.grid = grid;
        R = grid.length;
        C = grid[0].length;
        int left = R * C;

        for(int i = 0; i < R; i ++)
            for(int j = 0; j < C; j ++)
                if(grid[i][j] == 1){
                    start = i * C + j;
                    grid[i][j] = 0;
                }
                else if(grid[i][j] == 2){
                    end = i * C + j;
                    grid[i][j] = 0;
                }
                else if(grid[i][j] == -1)
                    left --;

        int visited = 0;
        return dfs(visited, start, left);
    }

    private int dfs(int visited, int v, int left){

        visited += (1 << v);
        left --;
        if(v == end && left == 0){
            visited -= (1 << v);
            return 1;
        }

        int x = v / C, y = v % C;
        int res = 0;
        for(int d = 0; d < 4; d ++){
            int nextx = x + dirs[d][0], nexty = y + dirs[d][1];
            int next = nextx * C + nexty;
            if(nextx >= 0 && nextx < R && nexty >= 0 && nexty < C) {
                if (grid[nextx][nexty] == 0 && (visited & (1 << next)) == 0) {
                    res += dfs(visited, next, left);
                }
            }
        }

        visited -= (1 << v);
        return res;
    }

    public static void main(String[] args) {
        BetterSolution980 solution980 = new BetterSolution980();
        System.out.println(solution980.uniquePathsIII(new int[][]{{1,0,0,0},{0,0,0,0},{0,0,2,-1}}));
    }
}