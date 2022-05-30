package application.service.task.word.dao;

import application.service.task.word.dao.entity.WordTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WordTaskRepository extends JpaRepository<WordTask, Long> {

    @Query(nativeQuery = true, value = "select * from word_task wt" +
            " left join learned_words lw on lw.word_id = wt.id" +
            " where lw.user_id is null and rownum() <= 1")
    Optional<WordTask> getNextTask(@Param("userId") Long userId);
}
