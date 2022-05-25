package application.filter;

import application.bot.utils.data.Data;
import application.filter.utils.ProceedMessage;
import application.user.dao.entity.User;
import application.user.service.UserService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("AuthFilter")
@Transactional
public class AuthFilter implements ProceedMessage {

    private final UserService userService;

    private final ProceedMessage proceedMessage;

    public AuthFilter(UserService userService, @Qualifier("settingsFilter") ProceedMessage proceedMessage) {
        this.userService = userService;
        this.proceedMessage = proceedMessage;
    }

    @Override
    public Data proceed(Update update, Data data) {
        Optional<User> byId = userService.getUser(update.message().chat().id());
        if(byId.isPresent()) {
            data.setUser(byId.get());
        } else {
            data.setUser(userService.registrationUser(update.message().chat().id()));
        }
        Data proceed = proceedMessage.proceed(update, data);
        userService.updateUser(data.getUser());
        return proceed;
    }
}
