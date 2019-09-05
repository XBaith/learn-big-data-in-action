package tree.bst.avl;

import org.junit.Test;
import tree.bst.BinarySearchTree;
import utils.FileOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AVLTest {
    @Test
    public void remove(){
        String input = "input/pride-and-prejudice.txt";
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile(input, words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

            System.out.println("is BST : " + map.isBinarySearch());
            System.out.println("is Balanced : " + map.isBalanced());

            for(String word: words){
                map.remove(word);
                if(!map.isBinarySearch() || !map.isBalanced())
                    throw new RuntimeException();
            }
        }

        System.out.println();
    }
}
