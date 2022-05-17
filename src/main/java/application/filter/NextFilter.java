package application.filter;

import application.filter.utils.ProceedMessage;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("nextFilter")
public class NextFilter implements ProceedMessage {
    @Override
    public String proceed(Update update, Map<String, Object> data) {
        return "Next";
    }
}
