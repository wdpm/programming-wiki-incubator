import java.util.DoubleSummaryStatistics;
import java.util.stream.DoubleStream;

public class Summarizing {
    public static void main(String[] args) {
        DoubleSummaryStatistics stats = DoubleStream.generate(Math::random)
                .limit(1_000_000)
                .summaryStatistics();

        System.out.println(stats);

        System.out.println("count: " + stats.getCount());
        System.out.println("min  : " + stats.getMin());
        System.out.println("max  : " + stats.getMax());
        System.out.println("sum  : " + stats.getSum());
        System.out.println("ave  : " + stats.getAverage());

        // JVM 在执行 limit(10) 时，为了能够提前截断无限流，会尝试预取一些元素。
        // 由于流的来源是无限的，并且内部处理机制会生成少量额外的元素来确保 limit 的边界正确。
        // 这些预取的元素会依次经过 map 中的 println，因此会触发打印。
        DoubleStream.generate(Math::random)
               .map(n -> {
                   System.out.println(n);
                   return n;
               })
//                .peek(System.out::println)
                .limit(10)
                .forEach(System.out::println);
    }
}

