package com.darksideofthedev;


import java.util.concurrent.Executors;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Init Main program" );

        /**
         * Run ExecutorService tasks
         */
        ExecutorServiceExample executorServiceExample = new ExecutorServiceExample();

        /**
          Use a </code>newCachedThreadPool()</> that Creates a thread pool that creates new threads as needed, but will reuse previously
          constructed threads when they are available
         */
        executorServiceExample
                .execute(Executors.newCachedThreadPool());


        /**
         * Run ScheduleExecutorServices tasks
         */
        ScheduledExecutorServiceExample scheduledExecutorServiceExample = new ScheduledExecutorServiceExample();

        /**
         * Use a <code>newScheduledThreadPool()</code> with a number of Threads equal
         * to <code>Runtime.getRuntime().availableProcessors()</code> that Creates a
         * thread pool that can schedule commands to run after a given delay or
         * to execute periodically.
         */
        scheduledExecutorServiceExample
                .executeSchedules(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));

        /**
         * Use a <code>newScheduledThreadPool()</code> with a number of Threads equal
         * to <code>Runtime.getRuntime().availableProcessors()</code> that Creates a
         * thread pool that can schedule commands to run after a given delay or
         * to execute periodically.
         *
         * Uses the <code>scheduleAtFixedRate()</code> and <code>scheduleWithFixedDelay()</code>
         */
        scheduledExecutorServiceExample
                .executeSchedulesAtFixed(Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors()));


        System.out.println("End Main program");
    }
}
