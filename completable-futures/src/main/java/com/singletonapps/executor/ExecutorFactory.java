package com.singletonapps.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ExecutorFactory {

    private static Executor executor;

    /**
     * Creates custom Executor for CompletableFutures
     */
    public static Executor getCustomExecutor(int nThreads){
        if(executor == null){
            return executor = Executors.newFixedThreadPool(Math.min(nThreads, 100), r -> {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            });
        }

        return executor;
    }
}
