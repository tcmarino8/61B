import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author
 */
public class BSTStringSetTest  {
    // FIXME: Add your own tests for your BST StringSet

    @Test
    public void testNothing() {
        // FIXME: Delete this function and add your own tests
    }

    @Test
    public void testFindNodeLeaf() {
        BSTStringSet tree = new BSTStringSet();
        tree.put("d");
        tree.put("b");
        tree.put("c");
        tree.put("e");
        tree.put("e");
        ArrayList<String> list = (ArrayList<String>) tree.asList();
        boolean check = tree.contains("c");
        boolean check2 = tree.contains(null);


    }
}
