package io.github.wdpm.jdk11;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class Chacha20Poly1305Example {
    public static void main(String[] args) throws Exception {

        String plainText = "This is a test message";
        byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);

        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "ChaCha20");

        byte[] nonceBytes = new byte[12];
        random.nextBytes(nonceBytes);
        IvParameterSpec nonce = new IvParameterSpec(nonceBytes);

        Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305/NONE/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, key, nonce);
        byte[] cipherBytes = cipher.doFinal(plainBytes);

        // Q: 为何需要创建新的cipher实例用于解密。不能重用之前的cipher吗？

        // 下面是来自chatGPT的回答：
        // 一般来说，在加密解密操作完成后，应该重新创建一个新的 cipher 实例。这是因为，每次加密解密操作完成后，
        // 内部状态都会被改变，并且许多加密算法是不可逆的，不能再使用同一个 cipher 实例进行加密解密。
        cipher = Cipher.getInstance("ChaCha20-Poly1305/NONE/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key, nonce);
        byte[] decryptedBytes = cipher.doFinal(cipherBytes);
        String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

        System.out.println("Original text: " + plainText);
        System.out.println("Decrypted text: " + decryptedText);
    }
}
