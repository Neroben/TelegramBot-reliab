package application.bot;

import application.bot.utils.KeyboardGenerator;
import application.bot.utils.SenderMessage;
import application.bot.utils.data.Data;
import application.bot.utils.data.Message;
import application.handler.utils.ProceedMessage;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class Telegram implements SenderMessage {
    private final TelegramBot telegramBot;
    private final ProceedMessage proceedMessage;
    private final KeyboardGenerator keyboardGenerator = new KeyboardGenerator();

    public Telegram(TelegramBot telegramBot,@Lazy @Qualifier("AuthHandler") ProceedMessage proceedMessage) {
        this.telegramBot = telegramBot;
        this.proceedMessage = proceedMessage;
        this.telegramBot.setUpdatesListener(this::proceedUpdates);
    }

    private int proceedUpdates(List<Update> updates) {
        updates.forEach(this::proceedMessage);
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void proceedMessage(Update update) {
        Data data = new Data();
        try {
            if (update.message() != null) {
                if (update.message().text() != null) {
                    proceedMessage.proceedText(update, data);
                }
            }
            if (update.callbackQuery() != null) {
                proceedMessage.proceedCallback(update, data);
            }
        }
        catch (Exception ex) {
            sendMessage(update.message().chat().id(), Message.builder()
                    .text("Я не умею это обрабатывать!")
                    .build());
        }
    }

    @Override
    public boolean sendMessage(Long chatId, Message message) {
        try {
            telegramBot.execute(new SendMessage(chatId, message.getText())
                    .replyMarkup(keyboardGenerator.generateWithSettings(message)));
            return true;
        } catch (Exception ex) {
            log.error("Send message exception: {}, chatId={}", ex.getMessage(), chatId);
            return false;
        }
    }

    @Override
    public boolean sendMessageWithInlineKeyboard(Long chatId, Message message) {
        try {
            telegramBot.execute(new SendMessage(chatId, message.getText())
                    .replyMarkup(keyboardGenerator.generateInline(message)));
            return true;
        } catch (Exception ex) {
            log.error("Send message exception: {}, chatId={}", ex.getMessage(), chatId);
            return false;
        }
    }
}