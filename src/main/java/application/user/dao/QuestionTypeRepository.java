package application.user.dao;

import application.user.dao.entity.QuestionEnum;
import application.user.dao.entity.QuestionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionTypeEntity, Long> {

    QuestionTypeEntity findByName(QuestionEnum name);

}
