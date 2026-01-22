package io.github.wdpm.jdk11;

import com.google.gson.Gson;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

/**
 * 321: HTTP Client (Standard)
 * <p>
 * 移除HttpResponse.BodyHandler.asString()，使用 HttpResponse.BodyHandlers.ofString() 代替
 *
 * @author evan
 * @date 2020/5/2
 * @refer https://github.com/biezhi/java11-examples/blob/master/src/main/java/io/github/biezhi/java11/http/Example.java
 */
public class HttpClientDemo {
    public static void main(String[] args) throws Exception {
        // http2();
        // syncGet("https://v1.hitokoto.cn/?c=f&encode=text");
        // asyncGet("https://v1.hitokoto.cn/?c=f&encode=text");
        // downloadFile();
        // uploadFile();
        // asyncPost();
        // proxy();
        // basicAuth();
    }

    // 同步调用 GET
    public static void syncGet(String uri) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(uri))
                                         .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    // 异步调用 GET
    public static void asyncGet(String uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(URI.create(uri))
                                         .build();

        CompletableFuture<HttpResponse<String>> responseCompletableFuture = client
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());
        responseCompletableFuture.whenComplete((resp, t) -> {
            if (t != null) {
                t.printStackTrace();
            } else {
                System.out.println(resp.statusCode());
                System.out.println(resp.body());
            }
        }).join();
    }

    // 访问 HTTP2 网址
    public static void http2() throws Exception {
        HttpClient.newBuilder()
                  .followRedirects(HttpClient.Redirect.NORMAL)
                  .version(HttpClient.Version.HTTP_2)
                  .build()
                  .sendAsync(HttpRequest.newBuilder()
                                        .uri(new URI("https://http2.akamai.com/demo"))
                                        .GET()
                                        .build(),
                          HttpResponse.BodyHandlers.ofString())
                  .whenComplete((resp, t) -> {
                      if (t != null) {
                          t.printStackTrace();
                      } else {
                          System.out.println(resp.body());
                          System.out.println(resp.statusCode());
                      }
                  }).join();
    }

    // 下载文件
    public static void downloadFile() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(new URI("https://v1.hitokoto.cn/"))
                                         .GET()
                                         .build();

        Path               tempFile = Files.createTempFile("hitokoto-home-v1", ".json");
        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(tempFile));
        System.out.println(response.statusCode());
        System.out.println(response.body());
        // C:\Users\evan\AppData\Local\Temp\hitokoto-home-v110226414409874448160.json
    }

    // 上传文件
    public static void uploadFile() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(new URI("http://localhost:8080/upload/"))
                                         .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("./README.md")))
                                         .build();

        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
        System.out.println(response.statusCode());
    }

    // 异步调用 POST
    public static void asyncPost() throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        Gson gson = new Gson();
        User user = new User();
        user.name = "someone";
        user.url = "https://example.com";

        String jsonBody = gson.toJson(user);

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(new URI("https://httpbin.org/post"))
                                         .header("Content-Type", "application/json")
                                         .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                                         .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
              .whenComplete((resp, t) -> {
                  if (t != null) {
                      t.printStackTrace();
                  } else {
                      System.out.println(resp.body());
                      System.out.println(resp.statusCode());
                  }
              }).join();
    }

    // 设置代理
    public static void proxy() throws Exception {
        // make sure v2ray is running at 1080 local port
        HttpClient client = HttpClient.newBuilder()
                                      .proxy(ProxySelector.of(new InetSocketAddress("127.0.0.1", 1080)))
                                      .build();

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(new URI("https://www.google.com"))
                                         .GET()
                                         .build();

        // fixme error: java.io.IOException: HTTP/1.1 header parser received no bytes
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    // basic 认证
    public static void basicAuth() throws Exception {
        HttpClient client = HttpClient.newBuilder()
                                      .authenticator(new Authenticator() {
                                          @Override
                                          protected PasswordAuthentication getPasswordAuthentication() {
                                              return new PasswordAuthentication("username", "password".toCharArray());
                                          }
                                      })
                                      .build();

        HttpRequest request = HttpRequest.newBuilder()
                                         .uri(new URI("https://labs.consol.de"))
                                         .GET()
                                         .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
    }

    // 并行请求
    public void getURIs(List<URI> uris) {
        HttpClient client = HttpClient.newHttpClient();

        List<HttpRequest> requests = uris.stream()
                                         .map(HttpRequest::newBuilder)
                                         .map(HttpRequest.Builder::build)
                                         .collect(toList());

        CompletableFuture<?>[] cfs = requests.stream()
                                             .map(request -> client
                                                     .sendAsync(request, HttpResponse.BodyHandlers.ofString()))
                                             .toArray(CompletableFuture<?>[]::new);
        CompletableFuture.allOf(cfs).join();

        // think how to collect multi futures' result
    }
}
