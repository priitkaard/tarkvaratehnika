package com.qaengine.database;

import com.qaengine.models.Question;
import com.qaengine.models.outputs.QuestionListElement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(
        "SELECT new com.qaengine.models.outputs.QuestionListElement(q.id, q.title, q.text, q.score, q.created, COUNT(c)) " +
        "FROM Question q LEFT JOIN q.comments c " +
        "GROUP BY q.id, q.title, q.text, q.score, q.created " +
        "ORDER BY q.created DESC"
    )
    List<QuestionListElement> listQuestions(Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Answer a set a.accepted = false where a.question = :question")
    void revertAnswerAccepted(Question question);

    @Query("SELECT q FROM Question q WHERE lower(q.title) LIKE lower(concat('%', ?1,'%'))")
    List<Question> getSimilarQuestions(String input, Pageable pageable);
}
