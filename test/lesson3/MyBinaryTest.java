package lesson3;

import kotlinx.html.I;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyBinaryTest {
    public void test() {
        removeTest();
        iteratorTest();
    }

    private void removeTest() {
        BinarySearchTree<Integer> n = new BinarySearchTree<>();
        int[] arr = {0, 7, 19, 22, 31, 32, 38, 39, 53, 57, 58, 70, 72, 73, 76, 87};
        for (int i: arr) {
            n.add(i);
        }

        assertTrue(n.contains(7));
        assertTrue(n.contains(53));
        assertTrue(n.contains(58));
        assertTrue(n.contains(87));
        n.remove(7);
        n.remove(53);
        n.remove(58);
        n.remove(87);
        assertFalse(n.contains(7));
        assertFalse(n.contains(53));
        assertFalse(n.contains(58));
        assertFalse(n.contains(87));
    }

    private void iteratorTest() {
        BinarySearchTree<Integer> n = new BinarySearchTree<>();
        int[] arr = {0, 7, 19, 22, 31, 32, 38, 39, 53, 57, 58, 70, 72, 73, 76, 87};
        for (int i: arr) {
            n.add(i);
        }

        Iterator<Integer> iterator = n.iterator();

        assertTrue(n.contains(38));
        assertTrue(n.contains(72));
        while (iterator.hasNext()) {
            int element = iterator.next();
            if ((element == 38) || (element == 72)) {
                iterator.remove();
            }
        }
        assertFalse(n.contains(38));
        assertFalse(n.contains(72));


    }

}
