package application.filter.utils;

import com.pengrad.telegrambot.model.Update;

import java.util.Map;

public interface ProceedMessage {

    String proceed(Update update, Map<String, Object> data);

}
