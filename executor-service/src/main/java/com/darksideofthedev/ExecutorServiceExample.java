package com.darksideofthedev;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class ExecutorServiceExample {

    void execute(ExecutorService executorService){

        AtomicInteger value = new AtomicInteger(0);

        try{

            /**
             * Two Runnable tasks submitted to the ExecutorService
             */
            executorService.submit(() -> IntStream
                    .rangeClosed(1, 50)
                    .forEach(n -> System.out.println(Thread.currentThread().getName() + " : " + (n + value.getAndIncrement()))
                    ));

            executorService.submit(() -> IntStream
                    .rangeClosed(1, 50)
                    .forEach(n -> System.out.println(Thread.currentThread().getName() + " : " + (n + value.getAndIncrement()))
                    ));

            /**
             * One Callable task to the ExecutorService to return a Future
             */
            Future<Integer> result = executorService.submit(() -> {
                Thread.sleep(5000);
                return IntStream.rangeClosed(1, 100)
                        .reduce(0,  (int a, int b) -> a + b);
            });

            Thread.sleep(1000L);

            System.out.println(Thread.currentThread().getName() + " : Doing more work before Future.get");

            /**
             * Do another Runnable task before asking for the Future
             */
            executorService.submit(() -> IntStream
                    .rangeClosed(1, 50)
                    .forEach(n -> System.out.println(Thread.currentThread().getName() + " : " + n)
                    ));

            System.out.println("Result : " + result.get());

            System.out.println("Result fetch finish");

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }

    }

}
