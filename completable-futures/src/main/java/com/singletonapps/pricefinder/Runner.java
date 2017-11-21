package com.singletonapps.pricefinder;

import com.singletonapps.util.Benchmark;

import java.util.List;
import java.util.function.Function;

public class Runner {

    public static void main(String[] args) {

        BestPriceFinderImproved bestPriceFinderImproved =
                new BestPriceFinderImproved();


        System.out.println("Sequential Stream");
        System.out.println(
                Benchmark.measureAlgorithmPerformance(bestPriceFinderImproved::findSequentialPrices, "Zenfone")
        );

        System.out.println("===========================================================================");
        System.out.println("Parallel Stream");
        System.out.println(
                Benchmark.measureAlgorithmPerformance(bestPriceFinderImproved::findParallelPrices, "Zenfone")
        );

        System.out.println("===========================================================================");
        System.out.println("CompletableFutures Stream");
        System.out.println(
                Benchmark.measureAlgorithmPerformance(bestPriceFinderImproved::findCompletableFuturesPrices, "Zenfone")
        );

        System.out.println("===========================================================================");
        System.out.println("CompletableFutures With custom Executor Stream");
        System.out.println(
                Benchmark.measureAlgorithmPerformance(bestPriceFinderImproved::findCompletableFuturesWithCustomExecutorPrices, "Zenfone")
        );


    }


}
