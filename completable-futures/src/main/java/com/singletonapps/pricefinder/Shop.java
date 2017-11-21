package com.singletonapps.pricefinder;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Shop implements Shopping {

    private final String name;

    private static ExecutorService executorService;

    public Shop(String name) {
        this.name = name;
    }

    @Override
    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * Basic implementation of a CompletableFuture
     */
    @Override
    public Future<Double> getPriceAsync(String product) {

        System.out.println(Thread.currentThread().getName() + " : Getting price Async");

        /*--------------*/
        CompletableFuture<Double> futurePrice =
                new CompletableFuture<>();

        getExecutor().submit(() -> {
            try{
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e){
                futurePrice.completeExceptionally(e);
            }
        });

        getExecutor().shutdown();

        return futurePrice;
        /*--------------*/
    }

    /** Simplified version of PriceAsync:
    * 1. It Creates a CompletableFuture to return a Future with value.
    * 2. Accept a Supplier function.
    * 3. It submit it to the ForkJoinPool (Can specify a specific Executor).
    * 4. It handles any Exception throw inside the pool */
    @Override
    public Future<Double> getPriceSupplyAsync(String product){
        //System.out.println(Thread.currentThread().getName() + " : Getting price Supply Async");
        /*--------------*/
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
        /*--------------*/
    }

    @Override
    public double calculatePrice(String product) {

        //System.out.println(Thread.currentThread().getName() + " : " + this.name + " : " + "Calculating price for " + product);
        delay();
        return new Random().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    @Override
    public void delay() {

        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static ExecutorService getExecutor(){
        if (executorService == null){
            executorService = Executors.newCachedThreadPool();
        }

        return executorService;
    }

    public String getName() {
        return name;
    }
}
