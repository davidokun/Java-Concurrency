package com.darksideofthedev;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ScheduledExecutorServiceExample {

    /**
     * Executes schedules task every given delay
     */
    void executeSchedules(ScheduledExecutorService scheduledExecutorService){


        /**
         * Runnable task that not return anything
         */
        Runnable task1 = () -> System.out.println("Hello Zoo");

        /**
         * Callable task that return a type
         */
        Callable<String> task2 = () -> "Monkey";

        /**
         * Schedule to run in 5 seconds
         */
        scheduledExecutorService.schedule(task1, 5, TimeUnit.SECONDS);

        /**
         * Schedule to run in 10 seconds and retunr a Future<?>
         */
        Future<?> result2 = scheduledExecutorService.schedule(task2, 10, TimeUnit.SECONDS);

        try {
            System.out.println(result2.get(20, TimeUnit.SECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }

        //scheduledExecutorService.shutdown();
    }


    /**
     * Execute scheduleAtFixed task.
     */
    void executeSchedulesAtFixed(ScheduledExecutorService scheduledExecutorService){


        AtomicInteger val = new AtomicInteger(0);

        Runnable task1 = () -> System.out.println(Thread.currentThread().getName() + " : Hello Zoo : " + val.getAndIncrement());


        /**
         * Creates a new task and submits it to the executor
         * every period, regardless of whether or not the previous task finished
         */
        scheduledExecutorService
                .scheduleAtFixedRate(task1, 10, 3, TimeUnit.SECONDS);

        /**
         * Creates a new task after the previous task has finished base on delay
         */
        scheduledExecutorService
                .scheduleWithFixedDelay(task1, 10, 3, TimeUnit.SECONDS);

    }
}
