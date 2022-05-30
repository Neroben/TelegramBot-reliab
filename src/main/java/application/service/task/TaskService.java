package application.service.task;

import application.service.user.dao.entity.QuestionEnum;
import application.service.user.dao.entity.User;

public interface TaskService {

    void getNextTask(User user, QuestionEnum questionType);

    boolean checkTask(User user, String answer);

}
