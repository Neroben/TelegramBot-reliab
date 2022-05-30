package application.bot.utils;

import application.bot.utils.data.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class KeyboardGenerator {

    public Keyboard generateWithSettings(Message message) {
        if (message.getKeyboard() == null) {
            return new ReplyKeyboardMarkup("");
        }
        return new ReplyKeyboardMarkup("")
                .addRow(message.getKeyboard().toArray(String[]::new))
                .addRow(getBackKey(message));
    }

    public Keyboard generateInline(Message message) {
        InlineKeyboardButton[] ts = message.getKeyboard().stream()
                .map(str -> new InlineKeyboardButton(str).callbackData(message.getSupportMessage() + "/" + str))
                .toArray(InlineKeyboardButton[]::new);
        return new InlineKeyboardMarkup()
                .addRow(ts);
    }

    private String getBackKey(Message message) {
        return message.getBackKey() != null ? message.getBackKey() : "";
    }

}
