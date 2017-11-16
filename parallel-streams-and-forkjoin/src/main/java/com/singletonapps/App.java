package com.singletonapps;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.LongStream;


public class App {

    static {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "16");
    }

    private static final int VALUE = 10_000_000;

    public static void main(String[] args) {

        Runtime rt = Runtime.getRuntime();
        long totalMem = rt.totalMemory();
        long maxMem = rt.maxMemory();
        long freeMem = rt.freeMemory();
        double megs = 1048576.0;

        System.out.println ("Total Memory: " + totalMem + " (" + (totalMem/megs) + " MiB)");
        System.out.println ("Max Memory:   " + maxMem + " (" + (maxMem/megs) + " MiB)");
        System.out.println ("Free Memory:  " + freeMem + " (" + (freeMem/megs) + " MiB)");

        List<Long> numbers = new ArrayList<>();

        LongStream.rangeClosed(1, VALUE)
                .forEach(numbers::add);



        System.out.println("===========================");

        System.out.println("Iterative sum done in: " +
                measureAlgorithmPerformance(SumNumbers::iterativeSum, VALUE) + " msecs\n");
        System.out.println("Stream Sequential sum done in: " +
                measureAlgorithmPerformance(SumNumbers::secuentialSum, VALUE) + " msecs\n");
        System.out.println("Stream Parallel sum done in: " +
                measureAlgorithmPerformance(SumNumbers::parallelSum, VALUE) + " msecs\n");
        System.out.println("Stream Ranged sum done in: " +
                measureAlgorithmPerformance(SumNumbers::rangedSum, VALUE) + " msecs\n");
        System.out.println("Stream Parallel Ranged sum done in: " +
                measureAlgorithmPerformance(SumNumbers::parallelRangedSum, VALUE) + " msecs\n");
        System.out.println("ForkJoin sum done in: " +
                measureAlgorithmPerformance(SumNumbers::forkJoinSum, VALUE) + " msecs\n");
        System.out.println("ForkJoinWithList sum done in: " +
                measureAlgorithmPerformanceForList(SumNumbers::forkJoinSumWithList, numbers) + " msecs\n");

    }


    public static long measureAlgorithmPerformance(Function<Long, Long> adder, long n) {

        long fastest = Long.MAX_VALUE;
        long result = 0;

        // Warm up
        for (int i = 0; i < 20; i++) {
            adder.apply(n);
        }

        // Execution and benchmark
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();
            result = adder.apply(n);
            long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            if (duration < fastest) fastest = duration;
        }

        System.out.println("Result: " + result);
        return fastest;
    }

    public static long measureAlgorithmPerformanceForList(Function<List<Long>, Long> adder, List<Long> n) {

        long fastest = Long.MAX_VALUE;
        long result = 0;

        // Warm up
        for (int i = 0; i < 20; i++) {
            adder.apply(n);
        }

        // Execution and benchmark
        for (int i = 0; i < 20; i++) {
            long start = System.nanoTime();
            result = adder.apply(n);
            long duration = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            if (duration < fastest) fastest = duration;
        }

        System.out.println("Result: " + result);
        return fastest;
    }
}
