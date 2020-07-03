package com.fund.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by jiangfei on 2020/6/27.
 */
public final class DefaultThreadFactory {

    private ThreadPoolTaskExecutor executor;
    private final static DefaultThreadFactory factory = new DefaultThreadFactory();

    private DefaultThreadFactory() {
        executor = createThreadPool();
    }

    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    public static void runLater(Runnable runnable) {
        factory.execute(runnable);
    }

    private ThreadPoolTaskExecutor createThreadPool() {
        String name = "process-";
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setThreadNamePrefix(name);
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setMaxPoolSize(250);
        taskExecutor.setQueueCapacity(0);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        taskExecutor.initialize();

        return taskExecutor;
    }

    public static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
