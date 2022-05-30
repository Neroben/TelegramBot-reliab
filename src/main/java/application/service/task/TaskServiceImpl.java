package application.service.task;

import application.bot.utils.SenderMessage;
import application.bot.utils.data.Message;
import application.service.task.word.WordTaskService;
import application.service.task.word.dao.entity.WordTask;
import application.service.user.dao.entity.QuestionEnum;
import application.service.user.dao.entity.User;
import application.service.user.dao.entity.UserState;
import application.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final WordTaskService wordTaskService;
    private final UserService userService;
    private final SenderMessage senderMessage;

    public void sendCurrentTask(User user) {
        Task currentTask = wordTaskService.getCurrentTask(user);
        switch (currentTask.getType()) {
            case WORDS -> senderMessage.sendMessageWithInlineKeyboard(user.getId(),
                    generateMessageQuestion((WordTask) currentTask));
            default -> throw new IllegalArgumentException("type is incorrect");
        }
    }

    @Override
    public void getNextTask(User user, QuestionEnum questionType) {
        userService.setState(user, UserState.DO_TASK);
        switch (questionType) {
            case WORDS -> {
                WordTask nextTask = wordTaskService.getNextTask(user);
                senderMessage.sendMessageWithInlineKeyboard(user.getId(), generateMessageQuestion(nextTask));
            }
//            case GRAMMAR -> nextTask = wordRepositoryInMemory.getNextTask(user.getId());
//            case LISTENING -> nextTask = wordRepositoryInMemory.getNextTask(user.getId());
//            case READING -> nextTask = wordRepositoryInMemory.getNextTask(user.getId());
            default -> throw new IllegalArgumentException("type is incorrect");
        }
    }

    private Message generateMessageQuestion(WordTask task) {
        String str = task.getRightAnswer() + ";" + task.getAnswers();
        List<String> answers = Arrays.stream(str.split(";")).collect(Collectors.toList());
        Collections.shuffle(answers);
        return Message.builder()
                .text("Как переводится фраза: " + task.getWord())
                .keyboard(answers)
                .backKey("Выйти") //todo как бы убрать это чудо слово
                .build();
    }

    @Override
    public boolean checkTask(User user, String answer) {
        return wordTaskService.checkTask(user, answer);
    }
}
