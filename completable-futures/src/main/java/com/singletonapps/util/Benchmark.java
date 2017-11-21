package com.singletonapps.util;

import java.util.List;
import java.util.function.Function;

public class Benchmark {

    public static List<String> measureAlgorithmPerformance(Function<String, List<String>> findPrices, String n) {

        long start = System.nanoTime();
        List<String> result = findPrices.apply(n);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");

        return result;
    }
}
