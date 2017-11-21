package com.singletonapps;

import java.util.concurrent.*;
import java.util.stream.Stream;

public class BasicFutureExample {


    public void executeSimpleFuture() {

        ExecutorService executorService = Executors.newCachedThreadPool();

        Future<Long> future = executorService.submit(this::longCalculation);

        System.out.println("Doing something else...");
        long result = doSomethingElse();
        System.out.println("Result of something else : " + result);

        try {
            System.out.println("Waiting for Future Result........");
            long futureResult = future.get();
            System.out.println("Future Result : " + futureResult);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        executorService.shutdown();


    }


    private Long longCalculation(){
        return Stream.iterate(1L, i -> i + 1)
                .limit(10_000_000)
                .parallel()
                .reduce(0L, (a,b) -> a + b);
    }

    private Long doSomethingElse() {

        return Stream.iterate(1L, i -> i + 1)
                .limit(1000)
                .reduce(0L, (a,b) -> a + b);

    }

}
