package com.elsief.telegram;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import static java.lang.System.getenv;

public class TimeDevouringApp {
    private static final Logger log = Logger.getLogger(TimeDevouringApp.class);

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                botsApi.registerBot(new Bot(getenv("BOT_NAME"), getenv("BOT_TOKEN"), Integer.parseInt(getenv("INTERVAL_IN_SECONDS"))));
            } catch (NumberFormatException numEx) {
                log.error("Interval in seconds should be a number!", numEx);
            } catch (TelegramApiException telEx) {
                log.error("Error while registering bot", telEx);
            }
        } catch (Exception e) {
            log.error("Error while creating bots API", e);
        }
    }
}
