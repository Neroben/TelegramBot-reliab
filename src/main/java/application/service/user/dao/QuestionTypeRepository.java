package application.service.user.dao;

import application.service.user.dao.entity.QuestionEnum;
import application.service.user.dao.entity.QuestionTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionTypeRepository extends JpaRepository<QuestionTypeEntity, Long> {

    QuestionTypeEntity findByName(QuestionEnum name);

}
