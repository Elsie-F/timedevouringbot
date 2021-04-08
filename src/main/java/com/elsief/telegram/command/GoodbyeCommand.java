package com.elsief.telegram.command;

import com.elsief.telegram.service.TimerExecutor;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.elsief.telegram.util.MessageUtil.getUserName;

public class GoodbyeCommand extends BotCommand {
    private static final Logger log = Logger.getLogger(GoodbyeCommand.class);
    private final TimerExecutor executor;

    public GoodbyeCommand(TimerExecutor executor) {
        super("goodbye", "Say goodbye to this bot");
        this.executor = executor;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        StringBuilder messageTextBuilder = new StringBuilder("Пока, ").append(getUserName(chat));

        SendMessage answer = new SendMessage();
        answer.setChatId(chat.getId().toString());
        answer.setText(messageTextBuilder.toString());

        try {
            absSender.execute(answer);
            executor.stop(chat.getId().toString());
        } catch (TelegramApiException e) {
            log.error("Error while sending message to user", e);
        }
    }
}
