package com.elsief.telegram.command;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StartCommand extends BotCommand {
    private static final Logger log = Logger.getLogger(StartCommand.class);
    private final ICommandRegistry commandRegistry;

    public StartCommand(ICommandRegistry commandRegistry) {
        super("start", "Запуск бота");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        StringBuilder startMessageBuilder = new StringBuilder("<b>Start</b>\n");
        startMessageBuilder.append("Поздравляю, вы начали пользоваться Ботом, Пожирающим Время!\n")
                .append("Список доступных команд:\n\n");

        for (IBotCommand botCommand : commandRegistry.getRegisteredCommands()) {
            startMessageBuilder.append(botCommand.toString()).append("\n\n");
        }

        SendMessage startMessage = new SendMessage();
        startMessage.setChatId(chat.getId().toString());
        startMessage.enableHtml(true);
        startMessage.setText(startMessageBuilder.toString());

        try {
            absSender.execute(startMessage);
        } catch (TelegramApiException e) {
            log.error("Error while sending message to user", e);
        }
    }
}
