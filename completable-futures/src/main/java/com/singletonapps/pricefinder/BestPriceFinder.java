package com.singletonapps.pricefinder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

public class BestPriceFinder {


    public void findBestPrice(String product){

        Shopping shop = new Shop("BestShop");

        long start = System.nanoTime();

//        Future<Double> futurePrice = shop.getPriceAsync(product);
        Future<Double> futurePrice = shop.getPriceSupplyAsync(product);

        long invocationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.println(Thread.currentThread().getName() + " : Invocation returned after : " + invocationTime + " msecs");

        System.out.println(Thread.currentThread().getName() + " : Doing something else..");
        long result = doSomethingElse();
        System.out.println(Thread.currentThread().getName() + " : Result of something else : " + result);

        try {
            double price = futurePrice.get(3, TimeUnit.SECONDS);
            System.out.printf(Thread.currentThread().getName() + " : Price is %.2f%n", price);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        long retrievalTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
        System.out.println(Thread.currentThread().getName() + " : Price returned after " + retrievalTime + " msecs");

    }

    private Long doSomethingElse() {

        return Stream.iterate(1L, i -> i + 1)
                .limit(10000000)
                .parallel()
                .reduce(0L, (a,b) -> a + b);

    }

}
