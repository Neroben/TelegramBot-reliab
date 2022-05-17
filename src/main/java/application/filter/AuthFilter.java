package application.filter;

import application.filter.utils.ProceedMessage;
import application.user.dao.entity.User;
import application.user.service.UserService;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service("AuthFilter")
public class AuthFilter implements ProceedMessage {

    private final UserService userService;

    private final ProceedMessage proceedMessage;

    public AuthFilter(UserService userService, @Qualifier("nextFilter") ProceedMessage proceedMessage) {
        this.userService = userService;
        this.proceedMessage = proceedMessage;
    }

    @Override
    public String proceed(Update update, Map<String, Object> data) {
        Optional<User> byId = userService.getUser(update.message().chat().id());
        if(byId.isPresent()) {
            data.put("user", byId.get());
            return proceedMessage.proceed(update, data);
        } else {
            userService.registrationUser(update.message().chat().id());
            return "Вы успешно зарегистрировались!";
        }
    }
}
