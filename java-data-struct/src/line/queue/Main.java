package line.queue;

import org.junit.Test;

public class Main {
    public static void main(String[] args) {
        ArrayQuene<Double> quene = new ArrayQuene<>();
        for(int i = 0 ; i < 21 ; i++){
            quene.enquene(i + 0.0);
            System.out.println(quene);
            if(i % 3 == 2){
                quene.dequene();
                System.out.println(quene);
            }
        }
        System.out.println(quene.getFront());
    }
    @Test
    public void testLinkedListQuene(){
        LinkedListQuene<Double> quene = new LinkedListQuene<>();
        for(int i = 0 ; i < 11 ; i++){
            quene.enquene(i + 0.0);
            System.out.println(quene);
            if(i % 3 == 2){
                quene.dequene();
                System.out.println(quene);
            }
        }
        System.out.println(quene.getFront());
    }

}
