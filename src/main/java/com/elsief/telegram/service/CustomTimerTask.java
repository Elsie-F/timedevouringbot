package com.elsief.telegram.service;

import com.elsief.telegram.Bot;

public class CustomTimerTask implements Runnable {
    private final String chatId;
    private final Bot bot;

    public CustomTimerTask(String chatId, Bot bot) {
        this.chatId = chatId;
        this.bot = bot;
    }

    @Override
    public void run() {
        bot.devourTime(chatId);
        System.out.println("time has passed");
    }
}
