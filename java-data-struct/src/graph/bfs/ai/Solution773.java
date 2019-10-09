package graph.bfs.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/sliding-puzzle/
 */
public class Solution773 {
    private static int[][] dirs = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public int slidingPuzzle(int[][] board) {
        Queue<String> queue = new LinkedList<>();
        HashMap<String, Integer> visited = new HashMap<>();

        String initalState = getInitalState(board);
        if("123450".equals(initalState))
            return 0;
        queue.add(initalState);
        visited.put(initalState, 0);

        while(!queue.isEmpty()){
            String curState = queue.poll();
            ArrayList<String> nexts = getNexts(curState);
            for(String next : nexts) {
                if(!visited.containsKey(next)){
                    queue.add(next);
                    visited.put(next, visited.get(curState) + 1);
                    if("123450".equals(next))
                        return visited.get(next);
                }
            }
        }

        return -1;
    }

    private ArrayList<String> getNexts(String curState) {
        char[] ns = curState.toCharArray();
        char[][] board = new char[2][3];

        int iter = 0;
        int zeroi = -1, zeroj = -1;
        for(int i = 0; i < 2; i ++)
            for(int j = 0; j < 3; j++){
                board[i][j] = ns[iter];
                if(board[i][j] == '0'){
                    zeroi = i;
                    zeroj = j;
                }
                iter++;
            }

        ArrayList<String> ret = new ArrayList<>();

        for(int d = 0; d < 4; d++){
            int movei = zeroi + dirs[d][0];
            int movej = zeroj + dirs[d][1];
            if(!(movei < 0 || movei >= 2 || movej < 0 || movej >= 3)) {

                char tmp;
                tmp = board[movei][movej];
                board[movei][movej] = board[zeroi][zeroj];
                board[zeroi][zeroj] = tmp;

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < 2; i++)
                    for (int j = 0; j < 3; j++) {
                        sb.append(board[i][j]);
                    }

                ret.add(sb.toString());

                tmp = board[movei][movej];
                board[movei][movej] = board[zeroi][zeroj];
                board[zeroi][zeroj] = tmp;
            }
        }

        return ret;
    }

    private String getInitalState(int[][] board) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 2; i++)
            for(int j = 0;j < 3; j++)
                sb.append(board[i][j]);
        return sb.toString();
    }

    public static void main(String[] args) {
        Solution773 solution773 = new Solution773();
        int[][] board = {{4,1,2},
                         {5,0,3}};
        System.out.println(solution773.slidingPuzzle(board));
    }

}
