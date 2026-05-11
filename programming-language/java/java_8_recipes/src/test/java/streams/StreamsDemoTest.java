package streams;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StreamsDemoTest {
    private final StreamsDemo demo = new StreamsDemo();

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
        assertThat(demo.sumFirstNBigDecimals(10)).isEqualTo(55, within(0.0001));
    }

    @Test
    public void testSumFirstNBigDecimalsWithPrecision() {
        System.out.println(demo.sumFirstNBigDecimalsWithPrecision(10));
    }

    @Test
    public void testSumRandoms1() {
        int num = 1000;
        double err = num * 0.05;
        assertThat(demo.sumRandoms1(num)).isEqualTo(num / 2.0, within(err));
    }

    @Test
    public void testSumRandoms2() {
        int num = 1000;
        double err = num * 0.05;
        assertThat(demo.sumRandoms2(num)).isEqualTo(num / 2.0, within(err));
    }

    @Test
    public void demoReduceWithAccumulator() {
        demo.sumRandoms2(10);
    }

    @Test
    public void testSumRandoms3() {
        int num = 1000;
        double err = num * 0.05;
        assertThat(demo.sumRandoms3(num)).isEqualTo(num / 2.0, within(err));
    }
}