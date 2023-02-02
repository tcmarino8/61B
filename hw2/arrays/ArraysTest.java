package arrays;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class ArraysTest {
    @Test
    public void catenateTest(){
        int[] a = new int[]{1, 2, 3};
        int[] b = new int[]{4, 5, 6};
        int[] result = new int[]{1, 2, 3, 4, 5, 6};

        assertArrayEquals(result, Arrays.catenate(a, b));
        int [] c = new int[]{};
        assertArrayEquals(a, Arrays.catenate(a, c));
        assertArrayEquals(a, Arrays.catenate(c, a));

    }
    @Test

    public void removeTest(){
        int[] a = new int[]{1, 2, 3, 4, 5};
        int[] result_a = new int[]{1, 4, 5};
        assertArrayEquals(result_a, Arrays.remove(a, 1, 2));
        int[] b = new int[]{2, 3, 4, 7, 8};
        int[] result_b = new int[]{7, 8};
        assertArrayEquals(result_b, Arrays.remove(b, 0, 3));
        }

    /** FIXME
     */

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
