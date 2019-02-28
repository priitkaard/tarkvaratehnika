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
            "SELECT new com.qaengine.models.outputs.QuestionListElement(" +
            "       q.id, q.title, q.text, q.score, q.created, COUNT(c), MAX(c.created), " +
            "       COUNT(a), MAX(a.created)" +
            ")" +
            "FROM Question q " +
            "LEFT JOIN q.comments c " +
            "LEFT JOIN q.answers a " +
            "WHERE lower(q.title) LIKE lower(concat('%', :query, '%')) " +
            "GROUP BY q.id, q.title, q.text, q.score, q.created"
    )
    List<QuestionListElement> listQuestions(String query, Pageable pageable);

    @Query(
            "SELECT new com.qaengine.models.outputs.QuestionListElement(" +
            "       q.id, q.title, q.text, q.score, q.created, COUNT(c), MAX(c.created), " +
            "       COUNT(a), MAX(a.created)" +
            ")" +
            "FROM Question q " +
            "LEFT JOIN q.comments c " +
            "LEFT JOIN q.answers a " +
            "WHERE lower(q.title) LIKE lower(concat('%', :query, '%')) " +
            "  AND q.category.id = :categoryId " +
            "GROUP BY q.id, q.title, q.text, q.score, q.created"
    )
    List<QuestionListElement> listQuestionsByCategory(Long categoryId, String query, Pageable pageable);


    @Modifying
    @Transactional
    @Query("update Answer a set a.accepted = false where a.question = :question")
    void revertAnswerAccepted(Question question);

    @Query("SELECT q FROM Question q WHERE lower(q.title) LIKE lower(concat('%', ?1,'%'))")
    List<Question> getSimilarQuestions(String input, Pageable pageable);
}
