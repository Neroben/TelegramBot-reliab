package application.service.task.word;


import application.service.task.Task;
import application.service.task.word.dao.WordTaskRepository;
import application.service.task.word.dao.entity.WordTask;
import application.service.user.dao.UserRepository;
import application.service.user.dao.entity.User;
import application.service.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordTaskService {

    private final WordTaskRepository wordTaskRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public WordTask getNextTask(User user) {
        WordTask nextTask = wordTaskRepository.getNextTask(user.getId()).orElseThrow();
        userService.setTask(user, nextTask);
        return nextTask;
    }

    public Task getCurrentTask(User user) {
        return wordTaskRepository.findById(user.getDoTaskId()).orElseThrow();
    }

    public Boolean checkTask(User user, String answer) {
        WordTask currentTask = (WordTask) getCurrentTask(user);
        return currentTask.getRightAnswer().equals(answer);
    }

}
