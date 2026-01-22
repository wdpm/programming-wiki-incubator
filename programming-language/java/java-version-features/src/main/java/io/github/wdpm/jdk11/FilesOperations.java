package io.github.wdpm.jdk11;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

/**
 * Files 文件操作
 *
 * @author evan
 * @date 2020/5/2
 */
public class FilesOperations {
    public static void main(String[] args) throws IOException {
        String text     = "Hello 2020.";
        String fileName = "hello2020.txt";

        // 写入文本
        Files.writeString(Paths.get(fileName), text);

        // 读取文本
        String readText = Files.readString(Paths.get(fileName));
        System.out.println("text equals?: " + text.equals(readText));

        // 删除文本
        Files.delete(Paths.get(fileName));
    }
}
