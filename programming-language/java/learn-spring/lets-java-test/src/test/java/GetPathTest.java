import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * get path test
 *
 * @author evan
 * @date 2020/5/19
 */
public class GetPathTest {

    @Test
    void byJavaIOFile() {
        String path         = "src/test/resources";
        File   file         = new File(path);
        String absolutePath = file.getAbsolutePath();
        assertTrue(absolutePath.endsWith("src\\test\\resources"));
    }

    @Test
    void byPath() {
        Path   resourceDirectory = Paths.get("src", "test", "resources");
        String absolutePath      = resourceDirectory.toFile().getAbsolutePath();
        assertTrue(absolutePath.endsWith("src\\test\\resources"));
    }

    @Test
    void byClassLoader() {
        String resourceName = "json-samples/no-specials.json";

        ClassLoader classLoader  = getClass().getClassLoader();
        File        file         = new File(classLoader.getResource(resourceName).getFile());
        String      absolutePath = file.getAbsolutePath();
        System.out.println(absolutePath);

        assertTrue(absolutePath.endsWith("no-specials.json"));
    }
}
