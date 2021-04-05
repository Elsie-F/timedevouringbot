package com.elsief.telegram.service;

import lombok.Getter;

public class CustomTimerTask implements Runnable {
    @Getter
    private final String CHAT_ID;

    public CustomTimerTask(String chatId) {
        CHAT_ID = chatId;
    }

    @Override
    public void run() {
        System.out.println("time has passed");
    }
}
