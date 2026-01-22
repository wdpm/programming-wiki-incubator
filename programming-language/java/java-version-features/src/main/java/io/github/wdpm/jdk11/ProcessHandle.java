package io.github.wdpm.jdk11;

/**
 * java.lang.ProcessHandle 新接口
 *
 * <li>java.lang.ProcessHandle.Info</li>
 * <li>java.lang.ProcessHandle</li>
 *
 * @author evan
 * @date 2020/5/2
 */
public class ProcessHandle {
    public static void main(String[] args) {
        java.lang.ProcessHandle processHandle = java.lang.ProcessHandle.current();
        System.out.println(processHandle.pid());

        java.lang.ProcessHandle.Info info = processHandle.info();
        System.out.println(info.toString());
    }
}
