package tree.bst.segment;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SegmentTreeTest {
    @Test
    public void build() {
        Integer[] arr = {1, 2, 3, 4, 5};
        SegmentTree<Integer> tree = new SegmentTree<>(arr, (e1, e2) -> e1 + e2);
        System.out.println(tree);
    }
    @Test
    public void querry(){
        Integer[] arr = {1, 2, 3, 4, 5};
        SegmentTree<Integer> segTree = new SegmentTree<>(arr, (e1, e2) -> e1 + e2);

        assertThat(6,is(segTree.querry(0,2)));
        assertThat(15,is(segTree.querry(0,4)));
    }

    @Test
    public void update(){
        Integer[] arr = {1, 2, 3, 4, 5};
        SegmentTree<Integer> segTree = new SegmentTree<>(arr, (e1, e2) -> e1 + e2);

        segTree.set(0,2);
        assertThat(7,is(segTree.querry(0,2)));
    }
}
