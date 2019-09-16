package graph;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class HashSetAndTreeSet {

    @Test
    public void oderTest(){
        Integer[] nums = new Integer[]{6,10,9,7,2,0,-1,-2,-11,13};
        List<Integer> list =Arrays.asList(nums);
        HashSet<Integer> hashSet = new HashSet<>(list);
        TreeSet<Integer> treeSet = new TreeSet<>(list);
        for(int n : hashSet){
            System.out.print(n + " ");
        }

        for(int n : treeSet){
            System.out.print(n + " ");
        }
    }

}
