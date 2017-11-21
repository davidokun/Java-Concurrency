package com.singletonapps.pricefinder;

import com.singletonapps.executor.ExecutorFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

public class BestPriceFinderImproved {

    private static Executor executor;


    static List<Shop> shops =
            Arrays.asList(
                    new Shop("BestPrice"),
                    new Shop("LestSaveBig"),
                    new Shop("MyFavoriteShop"),
                    new Shop("BuyItAll"),
                    new Shop("Amazon"),
                    new Shop("Linio"),
                    new Shop("BestBuy"),
                    new Shop("Alibaba"),
                    new Shop("Target"),
                    new Shop("Olimpica"),
                    new Shop("Exito")
            );


    /**
     * Sequential process
     */
    public List<String> findSequentialPrices(String product) {

        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    /**
     * Using parallelStream()
     */
    public List<String> findParallelPrices(String product) {

        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    public List<String> findCompletableFuturesPrices(String product) {

        List<CompletableFuture<String>> futurePrices =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " +
                                        shop.getPrice(product)
                        )).collect(Collectors.toList());

        return futurePrices.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public List<String> findCompletableFuturesWithCustomExecutorPrices(String product) {

        List<CompletableFuture<String>> futurePrices =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " +
                                        shop.getPrice(product), ExecutorFactory.getCustomExecutor(shops.size())
                        )).collect(Collectors.toList());

        return futurePrices.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }


}
