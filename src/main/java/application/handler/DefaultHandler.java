package application.handler;

import application.bot.utils.data.Data;
import application.handler.utils.ProceedMessage;
import application.service.settings.SettingsService;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("defaultHandler")
@RequiredArgsConstructor
public class DefaultHandler implements ProceedMessage {
    private final SettingsService settingsService;

    @Override
    public void proceedText(Update update, Data data) {
        settingsService.sendDefault(data.getUser());
    }

    @Override
    public void proceedCallback(Update update, Data data) {

    }
}
