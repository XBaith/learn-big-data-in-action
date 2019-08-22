package heap;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n = 1000000;
        MaxHeap<Integer> maxHeap = new MaxHeap<>();
        Random random = new Random();
        ;

        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            maxHeap.add(random.nextInt(Integer.MAX_VALUE));
        }

        for (int j = 0; j < n; j++) {
            arr[j] = maxHeap.extractMax();

        }
        for (int j = 1; j < n; j++) {
            if(arr[j - 1] < arr[j]){
                System.out.println(arr[j - 1] + " " +  arr[j] + " " + j);
                throw new IllegalArgumentException("Error!");
            }
        }

        System.out.println("ok!");
    }
}
