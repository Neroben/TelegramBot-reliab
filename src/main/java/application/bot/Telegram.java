package application.bot;

import application.bot.utils.SenderMessage;
import application.filter.utils.ProceedMessage;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class Telegram implements SenderMessage {
    public static final int INITIAL_CAPACITY = 3;
    private final TelegramBot telegramBot;

    private final ProceedMessage proceedMessage;

    public Telegram(TelegramBot telegramBot, @Qualifier("AuthFilter") ProceedMessage proceedMessage) {
        this.telegramBot = telegramBot;
        this.proceedMessage = proceedMessage;
        this.telegramBot.setUpdatesListener(this::proceedUpdates);
    }

    private int proceedUpdates(List<Update> updates) {
        updates.forEach(update -> {
            String message = proceedMessage.proceed(update, new HashMap<>(INITIAL_CAPACITY));
            sendMessage(update.message().chat().id(), message);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public boolean sendMessage(Long chatId, String message) {
        try {
            telegramBot.execute(new SendMessage(chatId, message));
            return true;
        } catch (Exception ex) {
            log.error("Send message exception: {}, chatId={}, message={}", ex.getMessage(), chatId, message);
            return false;
        }
    }
}