# Java-Concurrency
Samples using java.util.concurrent Classes


## 1. [Executor Service](https://github.com/davidokun/Java-Concurrency/tree/develop/executor-service)

Contains examples of ExecutorService class usage. It has ExecutorService and ScheduledExecutorService code snippets.

## 2. [Parallel Streams and ForkJoin](https://github.com/davidokun/Java-Concurrency/tree/develop/parallel-streams-and-forkjoin)

Contains examples to sum every number between 1 an n usign multiple types of iteration like sequential streams, parallel streams, Ranged Streams and implementations of the ForkJoin framework. 

It performs some benchmarks with each type of implementation.

### How to Run

1. Clone repository in local folder.
2. Run `mvn clean package`
3. Run Class `App.class`

Result may vary on each machine depending on the CPU and numbers of cores. An eample result should be like:

```bash
Total Memory: 506462208 (483.0 MiB)
Max Memory:   7497842688 (7150.5 MiB)
Free Memory:  501177352 (477.95996856689453 MiB)
===========================
Result: 50000005000000
Iterative sum done in: 6 msecs

Result: 50000005000000
Stream Sequential sum done in: 136 msecs

Result: 50000005000000
Stream Parallel sum done in: 127 msecs

Result: 50000005000000
Stream Ranged sum done in: 7 msecs

Result: 50000005000000
Stream Parallel Ranged sum done in: 1 msecs

Result: 50000005000000
ForkJoin sum done in: 59 msecs

Result: 50000005000000
ForkJoinWithList sum done in: 18 msecs
```
