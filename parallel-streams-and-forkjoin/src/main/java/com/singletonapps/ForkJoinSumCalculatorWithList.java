package com.singletonapps;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculatorWithList extends RecursiveTask<Long> {

    private final List<Long> numbers;
    private final int start;
    private final int end;

    private static final long THRESHOLD = 1_000_000;

    ForkJoinSumCalculatorWithList(List<Long> numbers) {
        this(numbers, 0, numbers.size());
    }

    private ForkJoinSumCalculatorWithList(List<Long> numbers, int start, int end) {
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
        ForkJoinSumCalculatorWithList leftTask =
                new ForkJoinSumCalculatorWithList(numbers, start, start + length/2);

        /* Sent to first half to a new Thread*/
        leftTask.fork();

        /* Create second task. */
        ForkJoinSumCalculatorWithList rightTask =
                new ForkJoinSumCalculatorWithList(numbers, start + length / 2, end);

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
            sum += numbers.get(i);
        }

        return sum;
    }
}
