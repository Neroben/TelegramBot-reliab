package application.user.service;

import application.user.dao.UserRepository;
import application.user.dao.entity.User;
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
}
