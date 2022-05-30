package application.service.settings;

import application.bot.utils.SenderMessage;
import application.bot.utils.data.Message;
import application.service.user.dao.QuestionTypeRepository;
import application.service.user.dao.entity.QuestionEnum;
import application.service.user.dao.entity.QuestionTypeEntity;
import application.service.user.dao.entity.User;
import application.service.user.dao.entity.UserState;
import application.service.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SettingsService {

    private final String SETTINGS_ANSWER;
    private final String SUPPORT_SELECT_MESSAGE;
    private final QuestionTypeRepository questionTypeRepository;

    private final UserService userService;

    private final SenderMessage senderMessage;

    public SettingsService(QuestionTypeRepository questionTypeRepository,
                           @Value("${telegram.message.settings-answer}") String settingsAnswer,
                           @Value("${telegram.message.select-task}") String supportSelectMessage, UserService userService, SenderMessage senderMessage) {
        this.questionTypeRepository = questionTypeRepository;
        SETTINGS_ANSWER = settingsAnswer;
        SUPPORT_SELECT_MESSAGE = supportSelectMessage;
        this.userService = userService;
        this.senderMessage = senderMessage;
    }

    public void findQuestionType(User user) {
        userService.setState(user, UserState.FIND_TASK);
        List<String> allQuestionTypes = questionTypeRepository.findAll()
                .stream()
                .map(QuestionTypeEntity::getName)
                .map(QuestionEnum::getName)
                .toList();
        senderMessage.sendMessage(user.getId(),
                Message.builder()
                        .text(SETTINGS_ANSWER)
                        .keyboard(allQuestionTypes)
                        .build());
    }

    public void sendDefault(User user) {
        senderMessage.sendMessage(user.getId(),
                Message.builder()
                        .text(SUPPORT_SELECT_MESSAGE)
                        .keyboard(Collections.emptyList())
                        .backKey(SUPPORT_SELECT_MESSAGE)
                        .build());
    }

}
