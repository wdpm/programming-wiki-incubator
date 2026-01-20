package io.github.wdpm.redis.bloom;

import io.rebloom.client.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 未测试
 *
 * @author evan
 * @date 2020/6/7
 */
public class BloomErrorRateTest {
    private String chars;

    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            builder.append((char) ('a' + i));
        }
        // a-z
        chars = builder.toString();
    }

    private String randomString(int n) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < n; i++) {
            int idx = ThreadLocalRandom.current().nextInt(chars.length());
            builder.append(chars.charAt(idx));
        }
        return builder.toString();
    }

    private List<String> randomUsers(int n) {
        List<String> users = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            users.add(randomString(64));
        }
        return users;
    }

    public static void main(String[] args) {
        BloomErrorRateTest bloomer    = new BloomErrorRateTest();
        List<String>       users      = bloomer.randomUsers(100000);
        List<String>       usersTrain = users.subList(0, users.size() / 2);
        List<String>       usersTest  = users.subList(users.size() / 2, users.size());

        Client client = new Client("192.168.137.12", 6378);
        client.delete("bloom");

        for (String uTrain : usersTrain) {
            client.add("bloom", uTrain);
        }

        int errCount = 0;
        for (String uTest : usersTest) {
            boolean ret = client.exists("bloom", uTest);
            if (ret) {
                errCount++;
            }
        }
        System.out.println("err count:" + errCount);
        System.out.println("usersTest total count:" + usersTest.size());
        System.out.println("err rate:" + errCount / usersTest.size());

        client.delete("bloom");

        // 对应 bf.reserve 指令
        client.createFilter("bloom", 50000, 0.001);
        for (String user : usersTrain) {
            client.add("bloom", user);
        }
        int falses = 0;
        for (String user : usersTest) {
            boolean ret = client.exists("bloom", user);
            if (ret) {
                falses++;
            }
        }
        System.out.printf("%d %d\n", falses, usersTest.size());
        System.out.println("err rate:" + falses / usersTest.size());
        client.delete("bloom");
        client.close();
    }
}