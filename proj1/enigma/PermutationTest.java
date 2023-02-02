package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author Tyler Marino
 */
public class PermutationTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /** Check that perm has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N - 1; i += 1) {
            System.out.println("in Perm, fromAlpha = " + fromAlpha);
            char c = fromAlpha.charAt(i);
            char e = toAlpha.charAt(i);
            char permutedC = perm.permute(c);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                         e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                         c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                         ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                         ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    @Test
    public void normal() {
        perm = new Permutation("(BACD)", new Alphabet("ABCD"));
        checkPerm("simple 1 cycle", "ABCD", "CADB");

    }
    @Test
    public void oneSelfMap() {
        perm = new Permutation("(AB) (DC)", new Alphabet("ABCDE"));
        checkPerm("Simple 2 cycle", "ABCDE", "BADCE");
    }
    @Test
    public void gapFail() {
        try {
            perm = new Permutation("(A CD) (BE)", new Alphabet("ABCDE"));
            checkPerm("space between perms", "ABCDE", "CEDAB");
        } catch (EnigmaException aa) {
            a = true;
        }
        assertTrue(a);
    }
    @Test
    public void extraOpenParen() {
        try {
            perm = new Permutation("((ACD)(BE)", new Alphabet("ABCDE"));
            checkPerm("Extra Parenthasis", "ABCDE", "CEDAB");
        } catch (EnigmaException bb) {
            b = true;
        }
        assertTrue(b);
    }
    @Test
    public void emptyCycle() {
        try {
            perm = new Permutation("(ACD) (BE) ()", new Alphabet("ABCDE"));
            checkPerm("empty cycle", "ABCDE", "CEDAB");
        } catch (EnigmaException cc) {
            L = true;
        }
        assertTrue(L);
    }
    @Test
    public void extraBothParen() {
        try {
            perm = new Permutation("((ACD))(BE)", new Alphabet("ABCDE"));
            checkPerm("Extra Parenthasis", "ABCDE", "CEDAB");
        } catch (EnigmaException dd) {
            d = true;
        }
        assertTrue(d);
    }
    @Test
    public void extraCloseParen() {
        try {
            perm = new Permutation(")(ACD)(BE)", new Alphabet("ABCDE"));
            checkPerm("Extra Parenthasis", "ABCDE", "CEDAB");
        } catch (EnigmaException ee) {
            K = true;
        }
        assertTrue(K);
    }
    @Test
    public void lowerCycle() {
        try {
            perm = new Permutation("(BACd)", new Alphabet("ABCD"));
            checkPerm("something", "ABCD", "CADB");
        } catch (EnigmaException ff) {
            f = true;
        }
        assertTrue(f);
    }
    @Test
    public void repeat() {
        try {
            perm = new Permutation("(BAC)(AD)", new Alphabet("ABCD"));
            checkPerm("something", "ABCD", "CABD");
        } catch (EnigmaException ff) {
            g = true;
        }
        assertTrue(g);
    }
    @Test
    public void test() {
        perm = new Permutation("(BACE)(D)", new Alphabet("ABCDEF"));
        checkPerm("something", "ABCDEF", "CAEDBF");
    }
    boolean b;
    boolean a;
    boolean L;
    boolean d;
    boolean K;
    boolean f;
    boolean g;

}
