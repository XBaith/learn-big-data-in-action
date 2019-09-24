package graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class HashSetAndTreeSet {

    @Test
    public void oderTest(){
        Integer[] nums = new Integer[]{6,10,9,7,2,0,1,2,1,11,13,-11};
        List<Integer> list =Arrays.asList(nums);
        HashSet<Integer> hashSet = new HashSet<>(list);
        TreeSet<Integer> treeSet = new TreeSet<>(list);
        for(int n : hashSet){
            System.out.print(n + " ");
        }
        System.out.println();

        for(int n : treeSet){
            System.out.print(n + " ");
        }
    }

    @Test
    public void hashTest(){
        int h;
        Integer key = -110;
        //Integer的hashCode
        System.out.println(Integer.toBinaryString(key.hashCode()));
        //无符号右移16位
        System.out.println(Integer.toBinaryString(key.hashCode() >>> 16));
        //最终结果
        System.out.println(Integer.toBinaryString((h = key.hashCode()) ^ (h >>> 16)));
    }

}
