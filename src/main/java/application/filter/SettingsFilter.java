package application.filter;

import application.bot.utils.data.Data;
import application.filter.utils.ProceedMessage;
import application.user.dao.QuestionTypeRepository;
import application.user.dao.entity.QuestionEnum;
import application.user.dao.entity.QuestionTypeEntity;
import com.pengrad.telegrambot.model.Update;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("settingsFilter")
public class SettingsFilter implements ProceedMessage {

    public static final String SETTINGS = "Settings";
    private final ProceedMessage proceedMessage;

    private final QuestionTypeRepository questionTypeRepository;

    public SettingsFilter(@Qualifier("taskFilter") ProceedMessage proceedMessage,
                          QuestionTypeRepository questionTypeRepository) {
        this.proceedMessage = proceedMessage;
        this.questionTypeRepository = questionTypeRepository;
    }


    @Override
    public Data proceed(Update update, Data data) {
        if (SETTINGS.equals(update.message().text())) {
            return proceedSettings(data);
        }
        if (data.getUser().getSettingsMod()) {
            QuestionEnum questionType = QuestionEnum.of(update.message().text());
            if (questionType != null) {
                Optional<QuestionTypeEntity> type = data.getUser().getQuestionTypeList().stream()
                        .filter(typeEntity -> typeEntity.getName() == questionType)
                        .findFirst();
                if (type.isPresent()) {
                    data.getUser().getQuestionTypeList().remove(type.get());
                    data.setMessage("Тип заданий удален!");
                } else {
                    data.getUser().getQuestionTypeList().add(questionTypeRepository.findByName(questionType));
                    data.setMessage("Тип заданий добавлен!");
                }
                data.getUser().setSettingsMod(false);
                return data;
            }
        }
        data.getUser().setSettingsMod(false);
        return proceedMessage.proceed(update, data);
    }

    private Data proceedSettings(Data data) {
        questionTypeRepository.findAll().forEach(type -> data.getKeyboard().add(type.getName().getName()));
        data.getUser().setSettingsMod(true);
        data.setMessage("Выберите тип задач (добавить/удалить)");
        return data;
    }

}
