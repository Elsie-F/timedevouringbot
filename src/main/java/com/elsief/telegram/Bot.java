package com.elsief.telegram;

import com.elsief.telegram.command.GoodbyeCommand;
import com.elsief.telegram.command.HelloCommand;
import com.elsief.telegram.service.TimerExecutor;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingCommandBot {
    private final String botName;
    private final String botToken;

    public Bot(String botName, String botToken, int intervalInSeconds) {
        super();
        this.botName = botName;
        this.botToken = botToken;
        TimerExecutor executor = new TimerExecutor(this, intervalInSeconds);
        register(new HelloCommand(executor));
        register(new GoodbyeCommand(executor));
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return botName;
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return botToken;
    }

    /**
     * Метод для приема сообщений, не являющихся командами.
     * @param update Содержит сообщение от пользователя.
     */
    @Override
    public void processNonCommandUpdate(Update update) {
        String message = update.getMessage().getText();
        sendMsg(update.getMessage().getChatId().toString(), message);
    }

    public void devourTime(String chatId) {
        sendMsg(chatId, "Time has passed");
    }

    private void sendMsg(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println("Exception: " + e.toString());
        }
    }
}
