package application.bot.utils;

import application.bot.utils.data.Message;

public interface SenderMessage {

    boolean sendMessage(Long chatId, Message message);

    boolean sendMessageWithInlineKeyboard(Long chatId, Message message);
}
