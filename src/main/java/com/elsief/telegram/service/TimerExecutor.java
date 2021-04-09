package com.elsief.telegram.service;

import com.elsief.telegram.Bot;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerExecutor {
    private static final Logger log = Logger.getLogger(TimerExecutor.class);
    private static final Map<String, ScheduledExecutorService> executorServiceMap = new ConcurrentHashMap<>();
    private final Bot bot;
    private final int intervalInSeconds;

    public TimerExecutor(Bot bot, int intervalInSeconds) {
        this.bot = bot;
        this.intervalInSeconds = intervalInSeconds;
    }

    public boolean hasExecutorForChat(String chatId) {
        return executorServiceMap.get(chatId) != null;
    }

    /**
     * Add a new CustomTimerTask to be executed
     *
     * @param chatId
     */
    public void startExecution(String chatId) {
        ScheduledExecutorService executorService = executorServiceMap.get(chatId);
        if (executorService == null) {
            executorService = Executors.newScheduledThreadPool(1);
            executorServiceMap.put(chatId, executorService);
            CustomTimerTask task = new CustomTimerTask(chatId, bot);
            executorService.scheduleAtFixedRate(task, intervalInSeconds, intervalInSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * Stop the thread
     */
    public void stop(String chatId) {
        ScheduledExecutorService executorService = executorServiceMap.get(chatId);
        if (executorService != null) {
            executorService.shutdown();
            executorServiceMap.remove(chatId);
            try {
                executorService.awaitTermination(1, TimeUnit.MINUTES);
            } catch (InterruptedException ex) {
                log.error("TimerExecutor waiting for termination was interrupted", ex);
            } catch (Exception e) {
                log.error("Bot threw an unexpected exception at TimerExecutor", e);
            }
        }
    }
}
