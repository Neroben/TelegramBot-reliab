package application.filter.utils;

import application.bot.utils.data.Data;
import com.pengrad.telegrambot.model.Update;

public interface ProceedMessage {

    Data proceed(Update update, Data data);

}
