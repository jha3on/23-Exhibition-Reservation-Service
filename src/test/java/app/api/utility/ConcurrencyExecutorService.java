package app.api.utility;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.function.Executable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ConcurrencyExecutorService {
    static int numberOfThreads = 10;
    static int numberOfThreadPool = 5;

    public static void execute(Executable executable, AtomicInteger successCount) throws InterruptedException {
        var executorService = Executors.newFixedThreadPool(numberOfThreadPool);
        var latch = new CountDownLatch(numberOfThreads);

        for (int i = 1; i <= numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    executable.execute(); // 실행
                    successCount.getAndIncrement(); // 실행 성공 후, 성공 횟수 증가
                } catch (Throwable e) {
                    log.info("[TEST/ERROR]: {}", e.getClass().getName());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
    }
}