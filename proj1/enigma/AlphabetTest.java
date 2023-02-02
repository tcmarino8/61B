package enigma;

import org.junit.Test;
/*
import org.junit.Rule;
import org.junit.rules.Timeout; */
import static org.junit.Assert.*;

public class AlphabetTest {
    @Test
    public void test1() {
        Alphabet test1 = new Alphabet("ABCD");
        assertEquals(4, test1.size());
        assertTrue(test1.contains('C'));
        assertFalse(test1.contains('F'));
        assertEquals(2, test1.toInt('C'));
        assertEquals('A', test1.toChar(0));

    }
}
