package io.github.wdpm.benchmark;

import java.util.Arrays;

/**
 * BadMicroBenchmark case 1
 *
 * @author evan
 * @date 2020/5/1
 */
public class BadMicroBenchmark {
    private static final int SIZE = 250_000_000;

    public static void main(String[] args) {
        try { // For machines with insufficient memory
            long[] la = new long[SIZE];
            System.out.println("setAll: " + Timer.duration(() -> Arrays.setAll(la, n -> n)));
            System.out.println("parallelSetAll: " + Timer.duration(() -> Arrays.parallelSetAll(la, n -> n)));
        } catch (OutOfMemoryError e) {
            System.out.println("Insufficient memory");
            System.exit(0);
        }
    }

    /*
     setAll: 213
     parallelSetAll: 215
    */

}