package application.service.task.word.dao.entity;

import application.service.task.Task;
import application.service.user.dao.entity.QuestionEnum;
import application.service.user.dao.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class WordTask implements Task {

    @Id
    private Long id;

    private String word;

    private String answers;

    private String rightAnswer;

    @ManyToMany
    @JoinTable(name = "learned_words",
            joinColumns = @JoinColumn(name = "word_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;

    @Override
    public QuestionEnum getType() {
        return QuestionEnum.WORDS;
    }
}
