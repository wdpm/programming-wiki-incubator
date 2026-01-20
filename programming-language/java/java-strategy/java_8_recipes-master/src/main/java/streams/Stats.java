package streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Stats {
    private NumberFormat nf = NumberFormat.getCurrencyInstance();

    public Team line2team(String line) {
        String[] vals = line.split("\\t+");
        int id = Integer.parseInt(vals[0]);
        String team = vals[1];
        double salary = 0.0;
        try {
            salary = nf.parse(vals[vals.length - 1]).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Team(id, team, salary);
    }

    public List<Team> parseSalaries() {
        List<Team> teams = new ArrayList<>();
        try (Stream<String> lines = Files.lines(
                Paths.get("src/main/resources/mlb_team_salaries_2017.txt"))) {
            teams = lines.map(this::line2team)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teams;
    }

    public void computeSalaryStatistics() {
        List<Team> teams = parseSalaries();
        DoubleSummaryStatistics teamStats = teams.stream()
                .mapToDouble(Team::getSalary)
                .collect(DoubleSummaryStatistics::new,
                        DoubleSummaryStatistics::accept,
                        DoubleSummaryStatistics::combine);
        printStats(teamStats);

        teamStats = teams.stream()
                .collect(Collectors.summarizingDouble(Team::getSalary));
        printStats(teamStats);
    }

    private void printStats(DoubleSummaryStatistics teamStats) {
        System.out.println(teamStats.getCount() + " teams");
        System.out.printf("  sum = %16s%n", nf.format(teamStats.getSum()));
        System.out.printf("  min = %17s%n", nf.format(teamStats.getMin()));
        System.out.printf("  max = %17s%n", nf.format(teamStats.getMax()));
        System.out.printf("  ave = %17s%n", nf.format(teamStats.getAverage()));
    }

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

        stats = new DoubleSummaryStatistics();
        stats.accept(1);

        Stats statistics = new Stats();
        statistics.parseSalaries().forEach(System.out::println);
        statistics.computeSalaryStatistics();
    }
}
