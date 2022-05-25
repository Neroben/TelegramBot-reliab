package application.bot.utils;

import application.bot.utils.data.Data;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class KeyboardGenerator {

    public Keyboard generateWithSettings(Data data) {
        return new ReplyKeyboardMarkup("")
                .addRow(data.getKeyboard().toArray(String[]::new))
                .addRow(data.getUser().getSettingsMod() ? "":"Settings");
    }

}
