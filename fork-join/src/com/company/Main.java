package com.company;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        System.out.println(Runtime.getRuntime().availableProcessors());

        long start = System.nanoTime();

        ForkJoinPool pool = new ForkJoinPool();
        SearchDirectory searchDir = new SearchDirectory("/home/davidokun/Documents","java");
        pool.execute(searchDir);
        List<String> fileList = searchDir.join();

        long total = System.nanoTime() - start;

        System.out.println("Total time : " + TimeUnit.NANOSECONDS.toMillis(total));

        System.out.println("The Search returned following files : " + fileList.size());

        fileList.stream()
                .forEach(System.out::println);

    }
}
