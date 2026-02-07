package io.github.wdpm.concurrency.taskexecution;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ThreadPerTaskWebServer
 * <p/>
 * Web server that starts a new thread for each request
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            final Socket connection = socket.accept();
            Runnable task = new Runnable() {
                public void run() {
                    handleRequest(connection);
                }
            };
            new Thread(task).start();
        }
    }

    /**
     * 这个方法必须为线程安全的
     *
     * @param connection
     */
    private static void handleRequest(Socket connection) {
        // request-handling logic here
    }
}
