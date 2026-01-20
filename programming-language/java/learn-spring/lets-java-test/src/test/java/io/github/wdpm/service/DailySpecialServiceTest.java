package io.github.wdpm.service;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JUnit 5 unit test examples
 *
 * @author evan
 * @date 2020/5/19
 */
class DailySpecialServiceTest {
    private static final String TEST_JSON_ROOT = "json-samples/";

    @Test
    public void GivenZeroSpecials_EmptyListIsReturned() throws Exception {
        String              json          = readJsonFromFile("no-specials.json");
        DailySpecialService service       = new DailySpecialService();
        List<String>        parsedFlavors = service.parseFlavorsFromJson(json);
        assertTrue(parsedFlavors.isEmpty());
    }

    @Test
    public void GivenThreeSpecials_ThenThreeFlavorsReturned() throws Exception {
        String              json          = readJsonFromFile("three-specials.json");
        DailySpecialService service       = new DailySpecialService();
        List<String>        parsedFlavors = service.parseFlavorsFromJson(json);
        assertEquals(3, parsedFlavors.size());
    }

    private String readJsonFromFile(String fileName) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        URI         uri         = classLoader.getResource(TEST_JSON_ROOT + fileName).toURI();
        Path        path        = Paths.get(uri);
        return new String(Files.readAllBytes(path));
    }
}