package com.wusuowei.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {
     //异步
    @Bean
    public TaskExecutor taskExecutor() {
        //Executor容易导致内存溢出
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        /**
         * 配置线程个数
         如果是CPU密集型任务，那么线程池的线程个数应该尽量少一些，一般为CPU的个数+1条线程(大量计算)
         如果是IO密集型任务，那么线程池的线程可以放的很大，如2*CPU的个数(IO操作)
         */
        executor.setCorePoolSize(10);
        //配置最大线程数
        executor.setMaxPoolSize(20);
        //配置队列大小
        executor.setQueueCapacity(20);
        // 空闲存活时间
        executor.setKeepAliveSeconds(60);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-task-thread-");
        return executor;
    }
}