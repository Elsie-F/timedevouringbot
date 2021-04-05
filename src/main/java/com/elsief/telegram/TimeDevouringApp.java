package com.elsief.telegram;

import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TimeDevouringApp {
    private static final Logger log = Logger.getLogger(TimeDevouringApp.class);

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                botsApi.registerBot(new Bot("TestTimeDevouringBot", "1764838700:AAGAEUH2GpgN2iitAXUodVTA3c9lJ3Vst-Y"));
            } catch (TelegramApiException e) {
                log.error("Error while registering bot", e);
            }
        } catch (Exception e) {
            log.error("Error while creating bots API", e);
        }
    }
}
