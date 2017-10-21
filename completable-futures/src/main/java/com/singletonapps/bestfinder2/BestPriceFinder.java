package com.singletonapps.bestfinder2;

import com.singletonapps.executor.ExecutorFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BestPriceFinder {



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

    public List<String> findSequentialPrices(String product){

        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findParallelPrices(String product){

        return shops.parallelStream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }


    public List<String> findCompletableFuturesComposePrices(String product){

        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product),
                        ExecutorFactory.getCustomExecutor(shops.size())))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() ->
                        Discount.applyDiscount(quote),
                        ExecutorFactory.getCustomExecutor(shops.size()))))
                .collect(Collectors.toList());

        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }



}
