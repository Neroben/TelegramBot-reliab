package application.service.task;

import application.service.user.dao.entity.QuestionEnum;

public interface Task {

    Long getId();

    QuestionEnum getType();

}
