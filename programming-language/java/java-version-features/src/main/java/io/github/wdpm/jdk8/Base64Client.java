package io.github.wdpm.jdk8;

import java.util.Base64;

/**
 * 原生支持Base64编码、解码。String、URL、MIME
 *
 * @author evan
 * @since 2020/4/19
 */
public class Base64Client {
    public static void main(String[] args) {
        String str = "password";
        // Encode String
        String encoded = Base64.getEncoder()
                               .encodeToString(str.getBytes());
        System.out.println(encoded);

        // Decode String
        String decoded = new String(Base64.getDecoder()
                                          .decode(encoded));
        System.out.println(decoded);


        String sampleURL = "https://www.example.org";
        // Encode URL
        String encodedURL = Base64.getUrlEncoder()
                                  .encodeToString(sampleURL.getBytes());
        System.out.println(encodedURL);

        // Decode URL
        String decodedURL = new String(Base64.getUrlDecoder()
                                             .decode(encodedURL));
        System.out.println(decodedURL);

        //MIME
    }
}
