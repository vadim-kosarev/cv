package dev.training;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;

public class Concurrency {
    private final static Logger logger = LoggerFactory.getLogger(Concurrency.class);


    private String work1() throws InterruptedException {
        Thread.currentThread().setName("Work1Thread");
        for (int i = 0; i < 50; i++) {
            logger.info("Work1[ONE] - Step " + i);
            Thread.sleep(250);
        }
        return "Work01 [ONE] Completed";
    }

    private String work2() throws InterruptedException {
        Thread.currentThread().setName("Work2Thread");
        for (int i = 0; i < 20; i++) {
            logger.info("Work2222[TWO] - Step " + i);
            Thread.sleep(250);
        }
        return "Work02 [TWO] Completed";
    }

    public static void test01() throws InterruptedException, ExecutionException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var obj = new Concurrency();
            var task1 = scope.fork(obj::work1);
            var task2 = scope.fork(obj::work2);
            scope.join();
            scope.throwIfFailed();
            logger.info("REsults: {}, {}", task1.get(), task2.get());
        }
    }

    public static void main(String... args) throws Exception {
        test01();
    }
}
