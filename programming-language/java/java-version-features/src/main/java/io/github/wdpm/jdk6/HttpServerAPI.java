package io.github.wdpm.jdk6;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Http Server API
 * <p>
 * 把一个 Http 请求和响应称为一个HttpExchange，HttpServer 将 HttpExchange 传给 HttpHandler实现类的 handle 方法
 *
 * @author evan
 * @original-author biezhi
 * @refer https://github.com/biezhi/learn-java8/blob/master/java8-growing
 * @since 2020/4/19
 */
public class HttpServerAPI {
    private static int count = 0;

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println("Request " + count++);
            System.out.println(he.getHttpContext()
                                 .getPath());

            // handle input
            InputStream is = he.getRequestBody();

            // handle output
            String response = "Just Java.";
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class MyCustomHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange he) throws IOException {
            System.out.println("Request " + count++);
            System.out.println(he.getHttpContext()
                                 .getPath());

            // handle input
            InputStream is         = he.getRequestBody();
            URI         requestURI = he.getRequestURI();
            System.out.println("requestURI: " + requestURI);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader    br  = new BufferedReader(isr);
            List<String> strings = br.lines()
                                     .collect(Collectors.toList());

            // handle output
            String response = requestURI.toString();
            he.sendResponseHeaders(200, response.length());
            OutputStream os = he.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    public static void main(String[] args) {
        try {
            HttpServer hs = HttpServer.create(new InetSocketAddress(8080), 0);
            hs.createContext("/", new MyHandler());
            hs.createContext("/custom", new MyCustomHandler());
            hs.setExecutor(null);
            hs.start();
            System.out.println("---begin---");
            System.out.println("Listening on " + hs.getAddress());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
