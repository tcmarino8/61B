package lists;

import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author FIXME
 */

public class ListsTest {

    @Test
    public void basicRunsTest() {
        IntList input = IntList.list(1, 2, 3, 1, 2);
        IntList run1 = IntList.list(1, 2, 3);
        IntList run2 = IntList.list(1, 2);
        IntListList result = IntListList.list(run1, run2);

        assertEquals(result, Lists.naturalRuns(input));

        IntList input2 = IntList.list(1, 3, 2, 5, 1);
        IntList run1_2 = IntList.list(1, 3);
        IntList run2_2 = IntList.list(2, 5);
        IntList run3_2 = IntList.list(1);
        IntListList result2 = IntListList.list(run1_2, run2_2, run3_2);

        assertEquals(result2, Lists.naturalRuns(input2));
    }

        /*IntList input3 = IntList.list(1, 2, 3, 4);
        assertTrue(Utils.equals(input3, Lists.naturalRuns(input3)));

        IntList input4 = IntList.list();
        assertTrue(Utils.equals(input4, Lists.naturalRuns(input4));


*/
        //FIXME: Add some assertion to make this a real test.


    //FIXME: Add more tests!

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
