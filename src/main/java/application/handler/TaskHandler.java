package application.handler;

import application.bot.utils.SenderMessage;
import application.bot.utils.data.Data;
import application.bot.utils.data.Message;
import application.handler.utils.ProceedMessage;
import application.service.task.TaskServiceImpl;
import application.service.user.dao.entity.QuestionEnum;
import com.pengrad.telegrambot.model.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service("taskHandler")
@Slf4j
public class TaskHandler implements ProceedMessage {

    private final ProceedMessage proceedMessage;
    private final TaskServiceImpl taskService;
    private final SenderMessage senderMessage;

    public TaskHandler(@Qualifier("defaultHandler") ProceedMessage proceedMessage, TaskServiceImpl taskService, SenderMessage senderMessage) {
        this.proceedMessage = proceedMessage;
        this.taskService = taskService;
        this.senderMessage = senderMessage;
    }

    @Override
    public void proceedText(Update update, Data data) {
        if (data.getUser().getDoTaskType() == QuestionEnum.WORDS) {
            if (taskService.checkTask(data.getUser(), update.message().text())) {
                senderMessage.sendMessage(data.getUser().getId(), Message.builder()
                        .text("Правильно!")
                        .keyboard(Collections.emptyList())
                        .build());
                taskService.getNextTask(data.getUser(), data.getUser().getDoTaskType());
                return;
            } else {
                senderMessage.sendMessage(data.getUser().getId(), Message.builder()
                        .text("Попробуй снова, у тебя все получится! ;)")
                        .keyboard(Collections.emptyList())
                        .build());
                taskService.sendCurrentTask(data.getUser());
                return;
            }
        }
        proceedMessage.proceedText(update, data);
    }

    @Override
    public void proceedCallback(Update update, Data data) {

    }
}
