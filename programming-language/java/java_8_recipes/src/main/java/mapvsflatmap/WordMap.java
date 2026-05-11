package mapvsflatmap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class WordMap {
    private final Path resourceDir = Paths.get("src/main/resources");
    private String fileName = "simple_file.txt";

    public Map<String, Long> createMap() {
        try (Stream<String> lines = Files.lines(resourceDir.resolve(fileName))) {
            return lines.flatMap(line -> line.isEmpty() ? Stream.empty() :
                            Stream.of(line.split(" ")))
                    .map(String::toLowerCase)
                    .collect(groupingBy(word -> word, counting()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
