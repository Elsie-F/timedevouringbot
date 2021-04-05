package com.elsief.telegram;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TimeDevouringApp {
    private static final Logger log = Logger.getLogger(TimeDevouringApp.class);
    private static final String BOT_NAME = "TestTimeDevouringBot";
    private static final String BOT_TOKEN = "1764838700:AAGAEUH2GpgN2iitAXUodVTA3c9lJ3Vst-Y";
    private static final int INTERVAL_IN_SECONDS = 5;

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                botsApi.registerBot(new Bot(BOT_NAME, BOT_TOKEN, INTERVAL_IN_SECONDS));
            } catch (TelegramApiException e) {
                log.error("Error while registering bot", e);
            }
        } catch (Exception e) {
            log.error("Error while creating bots API", e);
        }
    }
}
