package com.singletonapps.bestfinder2;

import com.singletonapps.util.Benchmark;

import java.util.List;
import java.util.function.Function;

public class Runner {

    public static void main(String[] args) {

        BestPriceFinder bestPriceFinder =
                new BestPriceFinder();

        System.out.println("Sequential Stream");
        System.out.println(
                Benchmark.measureAlgorithmPerformance(bestPriceFinder::findSequentialPrices, "Zenfone")
        );

        System.out.println("===========================================================================");
        System.out.println("Parallel Stream");
        System.out.println(
                Benchmark.measureAlgorithmPerformance(bestPriceFinder::findParallelPrices, "Zenfone")
        );

        System.out.println("===========================================================================");
        System.out.println("Composing CompletableFutures Stream");
        System.out.println(
                Benchmark.measureAlgorithmPerformance(bestPriceFinder::findCompletableFuturesComposePrices, "Zenfone")
        );
//
//        System.out.println("===========================================================================");
//        System.out.println("CompletableFutures With custom Executor Stream");
//        System.out.println(
//                measureAlgorithmPerformance(bestPriceFinderImproved::findCompletableFuturesWithCustomExecutorPrices, "Zenfone")
//        );


    }

}
