package line.array;

public class Main {

    public static void main(String[] args) {
	    Array<Integer> array = new Array<>();
	    for(int i = 0; i < 10 ; i++){
            array.addLast(i);
        }
        System.out.println(array);

        array.addLast(555);
        System.out.println(array);

        array.remove(4);
        System.out.println(array);

        array.removeElement(555);
        System.out.println(array);

        array.removeFirst();
        System.out.println(array);
        array.removeLast();
        System.out.println(array);

        System.out.println(array.contains(555));
        System.out.println(array.contains(5));
    }
}
