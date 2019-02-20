package com.qaengine.database;

import com.qaengine.models.Question;
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

    @Modifying
    @Transactional
    @Query("update Answer a set a.accepted = false where a.questionId=:questionId")
    void revertAnswerAccepted(@Param("questionId") Long questionId);

    @Query("SELECT q FROM Question q WHERE lower(q.title) LIKE lower(concat('%', ?1,'%'))")
    List<Question> getSimilarQuestions(String input, Pageable pageable);
}
