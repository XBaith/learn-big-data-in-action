package graph.bfs.ai;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 有两桶水桶，一个装５升，一个装３升，怎么用这两个桶得到４升水
 */
public class WaterPuzzle {
    private int[] pre;
    private int end;

    public WaterPuzzle() {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[100];
        pre = new int[100];

        queue.add(0);
        visited[0] = true;
        pre[0] = 0;

        while(!queue.isEmpty()) {
            int cur = queue.poll();
            int bucketA = cur / 10, bucketB = cur % 10;

            ArrayList<Integer> nexts = new ArrayList<>();
            //把A/B桶装满
            nexts.add(5 * 10 + bucketB);
            nexts.add(bucketA * 10 + 3);

            //A/B桶不装水
            nexts.add(0 * 10 + bucketB);
            nexts.add(bucketA * 10 + 0);

            //A桶的水倒入B桶
            int x = Math.min(bucketA, 3 - bucketB);
            nexts.add((bucketA - x) * 10 + (bucketB + x));
            //B桶的水倒入A桶
            int y = Math.min(5 - bucketA, bucketB);
            nexts.add((bucketA + y) * 10 + (bucketB - y));

            for(int next : nexts) {
                //当添加的多个状态中有相同的，其索引next相同，则visited数组在第一次遍历时便访问过，因此多个重复的状态并不影响结果
                if(!visited[next]) {
                    queue.add(next);
                    visited[next] = true;
                    pre[next] = cur;
                    if(next / 10 == 4 || next % 10 == 4){
                        end = next;
                        return;
                    }
                }
            }
        }

    }

    public Iterable<Integer> status() {
        LinkedList<Integer> ret = new LinkedList<>();
        int cur = end;
        while(cur != 0){
            ret.addFirst(cur);
            cur = pre[cur];
        }
        ret.addFirst(0);
        return ret;
    }

    public static void main(String[] args) {
        for(int s : (new WaterPuzzle()).status()) {
            System.out.println("bucket A: " + s / 10 + ", bucket B: " + s % 10);
        }
    }
}
