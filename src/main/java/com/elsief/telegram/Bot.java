package com.elsief.telegram;

import com.elsief.telegram.command.GoodbyeCommand;
import com.elsief.telegram.command.HelloCommand;
import com.elsief.telegram.command.HelpCommand;
import com.elsief.telegram.command.StartCommand;
import com.elsief.telegram.service.TimerExecutor;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.elsief.telegram.util.MessageUtil.generateReply;

public class Bot extends TelegramLongPollingCommandBot {
    private final String botName;
    private final String botToken;
    private final TimerExecutor executor;

    public Bot(String botName, String botToken, int intervalInSeconds) {
        super();
        this.botName = botName;
        this.botToken = botToken;
        executor = new TimerExecutor(this, intervalInSeconds);
        register(new HelloCommand(executor));
        register(new GoodbyeCommand(executor));
        register(new HelpCommand(this));
        register(new StartCommand(this));
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
        Message message = update.getMessage();
        String chatId = update.getMessage().getChatId().toString();
        if (this.executor.hasExecutorForChat(chatId)) {
            sendMsg(chatId, generateReply(message));
        }
    }

    public void devourTime(String chatId) {
        sendMsg(chatId, "Прошло время... Сейчас уже " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
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
