import static org.junit.Assert.*;
import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        int[][] test1 = new int[][]{{2, 6, 3}, {4, 3}};
        int[][] test2 = new int[][]{{2,6, 3}, {4,3, 4}, {1, 2} };
        assertEquals(6, MultiArr.maxValue(test1));
        assertEquals(6, MultiArr.maxValue(test2));



    }

    @Test
    public void testAllRowSums() {
        int[][] testx = new int[][]{{2, 6, 3}, {4, 3}};
        int[][] testy = new int[][]{{2,6, 3}, {4,3, 4}, { 1, 2} };
        int[][] testz = new int[][]{{1, 6, 7}, {1, 3, 4, 6}, {8, 4, 2}, {2, 2, 1, 3, 5, 1}};
        int [] expectx = new int[]{11, 7};
        int[] expecty = new int[]{11, 11, 3};
        int[] expectz = new int[]{14, 14, 14, 14};

        assertArrayEquals(expectx, MultiArr.allRowSums(testx));
        assertArrayEquals(expecty, MultiArr.allRowSums(testy));
        assertArrayEquals(expectz, MultiArr.allRowSums(testz));
    }
    /*@Test
    public void testrowAndColValues() {
        assertArrayEquals([2, 2], MultiArr.rowAndColValues([[1,2],[2,2]]));*/

    }


    /*Run the unit tests in this file.
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}*/
