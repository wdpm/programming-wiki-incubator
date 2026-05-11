package fileio;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JumbleTest {
    private final Jumble jumble = new Jumble();

    @Test
    void checkSolver() {
        assertAll(
                () -> assertEquals("actual", jumble.solve("cautla")),
                () -> assertEquals("goalie", jumble.solve("agileo"))
        );
    }

    @Test
    void checkParallelSolve() {
        List<String> strings = jumble.parallelSolve("zaaem", "rwdoc", "tlufan");
        System.out.println(strings);
        assertThat(strings).contains("amaze", "crowd", "flaunt");
        System.out.println(jumble.parallelSolve("snsaoe", "craigl", "ssevur",
                "lonelp", "nlahed", "ceitkl"));
    }
}