package application.service.user.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "question_type")
@Getter
@Setter
@NoArgsConstructor
public class QuestionTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionEnum name;

    @ManyToMany
    @JoinTable(name = "user2question_type",
            joinColumns = @JoinColumn(name = "question_type_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> userList;
}
