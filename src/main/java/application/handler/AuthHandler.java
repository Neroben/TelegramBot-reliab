package application.handler;

import application.bot.utils.data.Data;
import application.handler.utils.ProceedMessage;
import application.service.user.dao.entity.User;
import application.service.user.service.UserService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("AuthHandler")
@Transactional
public class AuthHandler implements ProceedMessage {
    private final UserService userService;

    private final ProceedMessage proceedMessage;

    public AuthHandler(UserService userService, @Qualifier("settingsHandler") ProceedMessage proceedMessage) {
        this.userService = userService;
        this.proceedMessage = proceedMessage;
    }

    @Override
    public void proceedText(Update update, Data data) {
        Optional<User> byId = userService.getUser(update.message().chat().id());
        if(byId.isPresent()) {
            data.setUser(byId.get());
        } else {
            data.setUser(userService.registrationUser(update.message().chat().id()));
        }
        proceedMessage.proceedText(update, data);
    }

    @Override
    public void proceedCallback(Update update, Data data) {
        update.callbackQuery();
//        Optional<User> byId = userService.getUser(update.callbackQuery());
//        if(byId.isPresent()) {
//            data.setUser(byId.get());
//        } else {
//            data.setUser(userService.registrationUser(update.message().chat().id()));
//        }
//        proceedMessage.proceedText(update, data);
    }
}
