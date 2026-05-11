package manning;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// From Java 8 and 9 in Action (now called Modern Java in Action)

// Sept 2, 2022. M1 Max 64GB RAM, 10 cores
// ParallelStreamBenchmark.iterativeSum             avgt   10    3.256 ± 0.011  ms/op
// ParallelStreamBenchmark.parallelLongStreamSum    avgt   10    0.484 ± 0.003  ms/op
// ParallelStreamBenchmark.parallelStreamSum        avgt   10   50.626 ± 1.095  ms/op
// ParallelStreamBenchmark.sequentialLongStreamSum  avgt   10    6.381 ± 0.027  ms/op
// ParallelStreamBenchmark.sequentialStreamSum      avgt   10   58.474 ± 0.555  ms/op

@SuppressWarnings("ALL")
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
public class ParallelStreamBenchmark {
    private static final long N = 10_000_000L;

    @Benchmark
    public long iterativeSum() {
        long result = 0;
        for (long i = 1L; i <= N; i++) {
            result += i;
        }
        return result;
    }

    @Benchmark  // Slowest possible stream
    public long sequentialStreamSum() {
        return Stream.iterate(1L, i -> i + 1)  // Stream<Long>
                .limit(N)
                .reduce(0L, Long::sum);
    }

    @Benchmark
    public long parallelStreamSum() {
        return Stream.iterate(1L, i -> i + 1)
                .limit(N)
                .parallel()
                .reduce(0L, Long::sum);
    }

    @Benchmark // Fastest possible stream
    public long sequentialLongStreamSum() {
        return LongStream.rangeClosed(1, N).sum();
    }

    @Benchmark
    public long parallelLongStreamSum() {
        return LongStream.rangeClosed(1, N)
                .parallel()
                .sum();
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }
}