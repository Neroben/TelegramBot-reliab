package application.service.user.service;

import application.service.task.Task;
import application.service.user.dao.entity.User;
import application.service.user.dao.entity.UserState;

import java.util.Optional;

public interface UserService {

    User registrationUser(Long charId);

    Optional<User> getUser(Long chatId);

    User updateUser(User user);

    User setState(User user, UserState state);

    User setTask(User user, Task task);
}
