package application.service.user.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_store")
@Getter
@Setter
public class User {

    @Id
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user2question_type",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "question_type_id"))
    private List<QuestionTypeEntity> questionTypeList;

    @Enumerated(EnumType.STRING)
    private UserState state;

    @Enumerated(EnumType.STRING)
    private QuestionEnum doTaskType;

    private Long doTaskId;

    public static User of(Long charId) {
        User user = new User();
        user.setId(charId);
        user.setState(UserState.NONE);
        return user;
    }

}
