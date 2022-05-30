package application.handler;

import application.bot.utils.data.Data;
import application.handler.utils.ProceedMessage;
import application.service.settings.SettingsService;
import application.service.task.TaskService;
import application.service.user.dao.entity.QuestionEnum;
import application.service.user.dao.entity.UserState;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("settingsHandler")
public class SettingsHandler implements ProceedMessage {
    private final String SELECT_SETTINGS_MESSAGE;
    private final ProceedMessage proceedMessage;
    private final TaskService taskService;
    private final SettingsService settingsService;

    public SettingsHandler(@Qualifier("taskHandler") ProceedMessage proceedMessage,
                           TaskService taskService,
                           SettingsService settingsService,
                           @Value("${telegram.message.select-task}") String supportSelectMessage) {
        SELECT_SETTINGS_MESSAGE = supportSelectMessage;
        this.proceedMessage = proceedMessage;
        this.taskService = taskService;
        this.settingsService = settingsService;
    }


    @Override
    public void proceedText(Update update, Data data) {
        String text = update.message().text();
        if (SELECT_SETTINGS_MESSAGE.equals(text)) {
            settingsService.findQuestionType(data.getUser());
            return;
        }
        if (data.getUser().getState() == UserState.FIND_TASK) {
            QuestionEnum questionType = QuestionEnum.of(text);
            if (questionType != null) {
                taskService.getNextTask(data.getUser(), questionType);
                return;
            }
        }
        proceedMessage.proceedText(update, data);
    }

    @Override
    public void proceedCallback(Update update, Data data) {

    }

}
