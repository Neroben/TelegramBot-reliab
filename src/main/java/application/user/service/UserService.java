package application.user.service;

import application.user.dao.entity.User;

import java.util.Optional;

public interface UserService {

    void registrationUser(Long charId);

    Optional<User> getUser(Long chatId);

    User updateUser(User user);

}
