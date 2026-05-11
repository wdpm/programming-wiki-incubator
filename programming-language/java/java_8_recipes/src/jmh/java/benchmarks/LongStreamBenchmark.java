package benchmarks;

import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

// Sept 2, 2022. M1 Max 64GB RAM, 10 cores
// Benchmark                       Mode  Cnt  Score   Error  Units
// LongStreamBenchmark.longValue   avgt   10  0.568 ± 0.015  ms/op
// LongStreamBenchmark.valueOf     avgt   10  2.624 ± 0.019  ms/op

// HOW TO RUN
// ./gradlew clean jmh "-Pjmh.include=LongStreamBenchmark"
// ./gradlew jmh "-Pjmh.include=LongStreamBenchmark" --rerun-tasks
// 结果位于 build/results/jmh/results.txt

@SuppressWarnings("ALL")
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
// @Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Fork(value = 1, warmups = 1)  // 快速测试
@Warmup(iterations = 1, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 2, time = 500, timeUnit = TimeUnit.MILLISECONDS)
public class LongStreamBenchmark {
    private static final long N = 10_000L;
    private List<Long> nums;

    private Logger logger = Logger.getLogger(this.getClass().getName());

    public LongStreamBenchmark() {
        logger.info("Creating list of " + N + " longs");
        nums = LongStream.rangeClosed(1, N)
                        .boxed()
                        .collect(Collectors.toList());
    }

    @Benchmark
    public long valueOf() {
        return nums.stream()
                .mapToLong(Long::valueOf)
                .sum();
    }

    @Benchmark
    public long longValue() {
        return nums.stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    @TearDown(Level.Invocation)
    public void tearDown() {
        System.gc();
    }
}