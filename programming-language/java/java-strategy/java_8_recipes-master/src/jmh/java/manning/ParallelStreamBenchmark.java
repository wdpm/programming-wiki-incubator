package manning;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// From Java 8 and 9 in Action

// Recent results:
//  Benchmark                                        Mode  Cnt   Score   Error  Units
//  ParallelStreamBenchmark.iterativeSum             avgt   40   6.441 ± 0.019  ms/op
//  ParallelStreamBenchmark.sequentialStreamSum      avgt   40  90.468 ± 0.613  ms/op
//  ParallelStreamBenchmark.parallelStreamSum        avgt   40  99.148 ± 3.065  ms/op
//  ParallelStreamBenchmark.sequentialLongStreamSum  avgt   40   6.191 ± 0.248  ms/op
//  ParallelStreamBenchmark.parallelLongStreamSum    avgt   40   6.571 ± 2.756  ms/op

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

    @Benchmark
    public long sequentialStreamSum() {
        return Stream.iterate(1L, i -> i + 1)
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

    @Benchmark
    public long sequentialLongStreamSum() {
        return LongStream.rangeClosed(1, N)
                .sum();
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