package line.list;

public class ListTest {

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        for(int i = 1 ; i < 11; i++){
            linkedList.add(i);
        }
        System.out.println(linkedList);

        linkedList.add(10, 999);
        System.out.println(linkedList);

        linkedList.removeByElement(999);
        System.out.println(linkedList);

        linkedList.removeLast();
        System.out.println(linkedList);

        System.out.println(linkedList.contains(999));

        System.out.println(linkedList.get(8));
    }




}
