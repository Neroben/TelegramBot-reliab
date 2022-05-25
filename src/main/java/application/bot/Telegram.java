package application.bot;

import application.bot.utils.KeyboardGenerator;
import application.bot.utils.SenderMessage;
import application.bot.utils.data.Data;
import application.filter.utils.ProceedMessage;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Telegram implements SenderMessage {
    public static final int INITIAL_CAPACITY = 3;
    private final TelegramBot telegramBot;

    private final ProceedMessage proceedMessage;

    private final KeyboardGenerator keyboardGenerator = new KeyboardGenerator();

    public Telegram(TelegramBot telegramBot, @Qualifier("AuthFilter") ProceedMessage proceedMessage) {
        this.telegramBot = telegramBot;
        this.proceedMessage = proceedMessage;
        this.telegramBot.setUpdatesListener(this::proceedUpdates);
    }

    private int proceedUpdates(List<Update> updates) {
        updates.forEach(update -> {
            Data data = new Data();
            proceedMessage.proceed(update, data);
            sendMessage(update.message().chat().id(), data);
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Override
    public boolean sendMessage(Long chatId, Data data) {
        try {
            telegramBot.execute(new SendMessage(chatId, data.getMessage())
                    .replyMarkup(keyboardGenerator.generateWithSettings(data)));
            return true;
        } catch (Exception ex) {
            log.error("Send message exception: {}, chatId={}, message={}", ex.getMessage(), chatId, data.getMessage());
            return false;
        }
    }
}