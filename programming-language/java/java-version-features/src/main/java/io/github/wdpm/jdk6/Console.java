package io.github.wdpm.jdk6;

/**
 * java.io.Console 类用来访问基于字符的控制台设备。
 * <p>
 * 一个JVM是否有可用的 Console 依赖于底层平台和 JVM 如何被调用。
 * </p>
 *
 * @author evan
 * @since 2020/4/19
 */
public class Console {
    public static void main(String[] args) {
        java.io.Console console = System.console();
        if (console != null) {
            String user = console.readLine(" Enter User: ", new Object[0]);
            String pwd  = new String(console.readPassword(" Enter Password: ", new Object[0]));
            console.printf(" User name is:%s ", new Object[]{user});
            console.printf(" Password is:%s ", new Object[]{pwd});
        } else {
            System.out.println("No Console!");
        }
    }
}
