package io.github.wdpm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * Custom Thread Pool Configuration
 *
 * <p>
 * By default, @EnableScheduling annotation creates a thread pool with only one thread.
 * The invocation of all @Scheduled tasks is queued and executed by an only thread
 * </p>
 */
@Configuration
public class SchedulerConfig implements SchedulingConfigurer {

    @Value("${thread.pool.size}")
    private int POOL_SIZE;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();

        scheduler.setPoolSize(POOL_SIZE);
        scheduler.setThreadNamePrefix("My-Scheduler-");
        scheduler.initialize();

        taskRegistrar.setTaskScheduler(scheduler);
    }
}
