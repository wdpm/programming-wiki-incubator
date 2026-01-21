package io.github.wdpm.algorithms.consistenthash.route;

import io.github.wdpm.algorithms.consistenthash.TreeMapConsistentHash;
import io.github.wdpm.algorithms.consistenthash.route.ConsistentHashHandle;
import io.github.wdpm.algorithms.consistenthash.route.LoopHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * RouteHandle driver
 *
 * @author evan
 * @date 2020/5/6
 */
public class RouteHandleClient {
    public static void main(String[] args) {
        ArrayList<String> ips = new ArrayList<>();
        ips.add("127.0.0.1");
        ips.add("127.0.0.2");
        ips.add("127.0.0.3");
        ips.add("127.0.0.4");

        testConsistentHash(ips);
        // testLoopHandle(ips);
    }

    private static void testConsistentHash(List<String> ips) {
        ConsistentHashHandle consistentHashHandle = new ConsistentHashHandle();
        consistentHashHandle.setHash(new TreeMapConsistentHash());
        consistentHashHandle.registerRouteServer(ips);
        String where1 = consistentHashHandle.getServer("wdpm");
        String where2 = consistentHashHandle.getServer("helloworld");
        System.out.println(where1);
        System.out.println(where2);
    }

    public static void testLoopHandle(List<String> ips) {
        LoopHandle loopHandle = new LoopHandle();
        loopHandle.registerRouteServer(ips);
        String first = loopHandle.getServer("wdpm");
        String next1 = loopHandle.getServer("wdpm");
        String next2 = loopHandle.getServer("wdpm");
        String next3 = loopHandle.getServer("wdpm");
        String next4 = loopHandle.getServer("wdpm");
        System.out.println(first);
        System.out.println(next1);
        System.out.println(next2);
        System.out.println(next3);
        System.out.println(next4);

        // 127.0.0.1
        // 127.0.0.2
        // 127.0.0.3
        // 127.0.0.4
        // 127.0.0.1
    }
}
