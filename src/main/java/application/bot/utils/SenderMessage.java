package application.bot.utils;

import application.bot.utils.data.Data;

public interface SenderMessage {

    boolean sendMessage(Long chatId, Data data);

}
