package io.github.wdpm.jdk18;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SPIExample {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress inetAddress = InetAddress.getByName("www.bilibili.com");
        System.out.println(inetAddress.getHostAddress());
    //    183.232.239.19
    }
}
