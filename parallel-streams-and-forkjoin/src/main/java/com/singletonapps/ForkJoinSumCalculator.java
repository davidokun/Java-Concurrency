package com.singletonapps;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    private static final long THRESHOLD = 100_000;

    ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }


    @Override
    protected Long compute() {

        int length = end - start;

        if (length <= THRESHOLD) {
            return computeSequentially();
        }

        /* Create first task. */
        ForkJoinSumCalculator leftTask =
                new ForkJoinSumCalculator(numbers, start, start + length/2);

        /* Sent to first half to a new Thread*/
        leftTask.fork();

        /* Create second task. */
        ForkJoinSumCalculator rightTask =
                new ForkJoinSumCalculator(numbers, start + length / 2, end);

        /* Compute right task in same Thread*/
        long rightResult = rightTask.compute();

        /* Wait for completion */
        long leftResult = leftTask.join();

        /* Merge results */
        return leftResult + rightResult;

    }

    private Long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }

        return sum;
    }
}
