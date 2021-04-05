package com.elsief.telegram.service;

import com.elsief.telegram.Bot;

import java.util.concurrent.atomic.AtomicBoolean;

public class CustomTimerTask implements Runnable {
    private final String chatId;
    private final Bot bot;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public CustomTimerTask(String chatId, Bot bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    public boolean isRunning() {
        return this.isRunning.get();
    }

    public void setRunning() {
        this.isRunning.set(true);
    }

    @Override
    public void run() {
        bot.devourTime(chatId);
    }
}
