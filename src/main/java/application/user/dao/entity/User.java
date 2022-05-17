package application.user.dao.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_entity")
@Getter
@Setter
public class User {

    @Id
    private Long id;

    public static User of(Long charId) {
        User user = new User();
        user.setId(charId);
        return user;
    }

}
