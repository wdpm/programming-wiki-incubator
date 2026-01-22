package io.github.wdpm.jdk11;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * JEP 323: Local-Variable Syntax for Lambda Parameters
 *
 * @author evan
 * @date 2020/5/2
 */
public class LocalVarVariable
{
    public static void main(String[] args) throws IOException
    {
        var list = new ArrayList<String>();
        System.out.println(list instanceof ArrayList);

        var stream = list.stream();
        System.out.println(stream instanceof Stream);

        var newList = List.of("hello", "world");
        newList.forEach(System.out::println);

        String fileName = "./LICENSE";
        var    path     = Paths.get(fileName);
        var    bytes    = Files.readAllBytes(path);
        System.out.println("byte[]: " + bytes);

        for (byte aByte : bytes) {
            // do stuff
        }

        try (var fis = new FileInputStream(new File(""))) {
            System.out.println(fis);
        } catch (Exception e) {
            //ignore
        }
    }
}
