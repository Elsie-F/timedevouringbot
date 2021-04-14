package com.elsief.telegram.util;

import com.google.common.collect.ImmutableMap;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;

public class MessageUtil {
    private static final Map<String, List<String>> replies =
            ImmutableMap.<String, List<String>>builder().put("photo", singletonList("Какая красота!"))
                    .put("voice", singletonList("Кто вообще придумал голосовые сообщения?"))
                    .put("audio", singletonList("Потом послушаю"))
                    .put("sticker", singletonList("Крутой стикерпак"))
                    .put("video", Arrays.asList("Потом посмотрю", "Видео - сложно!"))
                    .put("location", singletonList("Не узнаю это место"))
                    .put("text", Arrays.asList("Как интересно!", "Весьма неоднозначный момент", "Кто бы мог подумать"))
                    .build();

    public static String getUserName(Chat chat) {
        return (chat.getUserName() == null || chat.getUserName().isBlank())
                ? String.format("%s %s", chat.getFirstName(), chat.getLastName())
                : chat.getUserName();
    }

    public static String generateDevourTimeMessage(int intervalInSeconds) {
        return intervalInSeconds == 3600
                ? "Уже час прошёл!"
                : "Прошло время... Сейчас уже " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /**
     * Формирование ответа на сообщение в соответствии с его содержимым
     *
     * @param message - сообщение
     * @return - ответ
     */
    public static String generateReply(Message message) {
        List<String> answers = replies.get(getType(message));
        return answers.get((int) (Math.random() * answers.size()));
    }

    private static String getType(Message message) {
        if (message.hasPhoto()) {
            return "photo";
        } else if (message.hasVoice()) {
            return "voice";
        } else if (message.hasAudio()) {
            return "audio";
        } else if (message.hasSticker()) {
            return "sticker";
        } else if (message.hasVideo() || message.hasVideoNote()) {
            return "video";
        } else if (message.hasLocation()) {
            return "location";
        } else {
            return "text";
        }
    }
}
