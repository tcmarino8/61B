import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {


    @Test
    public void testNumYears() {
        assertEquals( 11, CompoundInterest.numYears(2033));
        assertEquals( 1, CompoundInterest.numYears(2023));
        assertEquals( 100, CompoundInterest.numYears(2122));
        assertEquals( 0, CompoundInterest.numYears(2022));
        /** Sample assert statement for comparing integers.

        assertEquals(0, 0); */
    }

    @Test
    public void testFutureValue() {
        // When working with decimals, we often want to specify a certain
        // range of "wiggle room", or tolerance. For example, if the answer
        // is 5.04, but anything between 5.02 and 5.06 would be okay too,
        // then we can do assertEquals(5.04, answer, .02).

        // The variable below can be used when you write your tests.
        double tolerance = 0.01;
        assertEquals( 214.36, CompoundInterest.futureValue(100, 10, 2030), tolerance);
        assertEquals( 657.51, CompoundInterest.futureValue(5, 5, 2122), tolerance);
        assertEquals( 5780.04, CompoundInterest.futureValue(20, 12, 2072), tolerance);
        assertEquals( 60, CompoundInterest.futureValue(60, 8, 2022), tolerance);
        assertEquals( -55.2, CompoundInterest.futureValue(-60, 8, 2023), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals( 182.37, CompoundInterest.futureValueReal(100, 10, 2030, 2), tolerance);
        assertEquals( 31.26, CompoundInterest.futureValueReal(5, 5, 2122, 3), tolerance);
        assertEquals( 2.59, CompoundInterest.futureValueReal(20, 0, 2072, 4), tolerance);
        assertEquals( 60, CompoundInterest.futureValueReal(60, 8, 2022, .5), tolerance);
        assertEquals( -60, CompoundInterest.futureValueReal(-60, 8, 2022, .5), tolerance);
        assertEquals( -54.92, CompoundInterest.futureValueReal(-60, 8, 2023, .5), tolerance);

    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2024, 10), tolerance);
        assertEquals(21000, CompoundInterest.totalSavings(10000, 2023, 10), tolerance);
        assertEquals(5000, CompoundInterest.totalSavings(5000, 2022, 10), tolerance);

    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(16220.65, CompoundInterest.totalSavingsReal(5000, 2024, 10, 1), tolerance);
        assertEquals(20580, CompoundInterest.totalSavingsReal(10000, 2023, 10, 2), tolerance);
        assertEquals(5000, CompoundInterest.totalSavingsReal(5000, 2022, 10, 3), tolerance);


    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
