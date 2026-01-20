package streams;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class StreamsDemoTest {
    private StreamsDemo demo = new StreamsDemo();

    @Test
    public void testJoinStream() {
        assertEquals("this is a list of strings", demo.joinStream());
    }

    @Test
    public void testJoinUpperCase() {
        assertEquals("THIS IS A LIST OF STRINGS", demo.joinUpperCase());
    }

    @Test
    public void testGetTotalLength() {
        assertEquals(20, demo.getTotalLength());
    }

    @Test
    public void testSumFirstNBigDecimals() {
        assertThat(demo.sumFirstNBigDecimals(10),
                is(closeTo(1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 + 10, 0.01)));
    }

    @Test
    public void testSumFirstNBigDecimalsWithPrecision() {
        System.out.println(demo.sumFirstNBigDecimalsWithPrecision(10));
    }

    @Test
    public void testSumRandoms1() {
        int num = 1000;
        double err = num * 0.05;
        assertThat(demo.sumRandoms1(num), is(closeTo(num / 2, err)));
    }

    @Test
    public void testSumRandoms2() {
        int num = 1000;
        double err = num * 0.05;
        assertThat(demo.sumRandoms2(num), is(closeTo(num / 2, err)));
    }

    @Test
    public void demoReduceWithAccumulator() {
        demo.sumRandoms2(10);
    }

    @Test
    public void testSumRandoms3() {
        int num = 1000;
        double err = num * 0.05;
        assertThat(demo.sumRandoms3(num), is(closeTo(num / 2, err)));
    }
}