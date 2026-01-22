package io.github.wdpm.jdk7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Try with resource 确保资源在处理完成后被关闭
 * <p>
 * 使用限制：实现了 java.lang.AutoCloseable 接口/java.io.Closeable 接口的对象。
 * </p>
 *
 * @author evan
 * @since 2020/4/19
 */
public class TryWithResource {
    public static void main(String[] args) {
        String path = TryWithResource.class.getResource("/test.js")
                                           .getPath();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            while (null != line) {
                System.out.println(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
