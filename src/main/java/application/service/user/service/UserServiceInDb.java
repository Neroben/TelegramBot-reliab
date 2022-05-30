package application.service.user.service;

import application.service.task.Task;
import application.service.user.dao.UserRepository;
import application.service.user.dao.entity.User;
import application.service.user.dao.entity.UserState;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceInDb implements UserService {

    private final UserRepository userRepository;


    public UserServiceInDb(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registrationUser(Long charId) {
        return userRepository.save(User.of(charId));
    }

    @Override
    public Optional<User> getUser(Long chatId) {
        return userRepository.findById(chatId);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User setState(User user, UserState state) {
        user.setState(state);
        return userRepository.save(user);
    }

    @Override
    public User setTask(User user, Task task) {
        user.setDoTaskId(task.getId());
        user.setDoTaskType(task.getType());
        return userRepository.save(user);
    }
}
