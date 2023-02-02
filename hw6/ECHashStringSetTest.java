import org.junit.Test;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class ECHashStringSetTest  {
    // FIXME: Add your own tests for your ECHashStringSetTest

    @Test
    public void testNothing() {
        ECHashStringSet buckets =
                new ECHashStringSet();
        buckets.put("a");
        buckets.put("b");
        buckets.put("e");
        buckets.put("i");
        buckets.put("d");
        buckets.put("c");
        boolean check = buckets.contains("c");

        // FIXME: Delete this function and add your own tests
    }
}
