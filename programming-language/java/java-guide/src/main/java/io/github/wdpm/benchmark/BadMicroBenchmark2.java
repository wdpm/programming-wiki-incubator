package io.github.wdpm.benchmark;

import java.util.Arrays;
import java.util.Random;
import java.util.SplittableRandom;

/**
 * BadMicroBenchmark case 2
 *
 * @author evan
 * @date 2020/5/1
 */
public class BadMicroBenchmark2 {
    static final int SIZE = 5_000_000;

    public static void main(String[] args) {
        long[] la = new long[SIZE];

        Random r = new Random();
        System.out.println("parallelSetAll: " + Timer.duration(() -> Arrays.parallelSetAll(la, n -> r.nextLong())));
        System.out.println("setAll: " + Timer.duration(() -> Arrays.setAll(la, n -> r.nextLong())));

        SplittableRandom sr = new SplittableRandom();
        System.out.println("parallelSetAll: " + Timer.duration(() -> Arrays.parallelSetAll(la, n -> sr.nextLong())));
        System.out.println("setAll: " + Timer.duration(() -> Arrays.setAll(la, n -> sr.nextLong())));
    }

    /*
     parallelSetAll: 963
     setAll: 100
     parallelSetAll: 69
     setAll: 14
    */
}