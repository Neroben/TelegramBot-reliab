package application.handler.utils;

import application.bot.utils.data.Data;
import com.pengrad.telegrambot.model.Update;

public interface ProceedMessage {

    void proceedText(Update update, Data data);

    void proceedCallback(Update update, Data data);
}
