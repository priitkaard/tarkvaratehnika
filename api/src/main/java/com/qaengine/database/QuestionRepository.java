package com.qaengine.database;

import com.qaengine.models.Answer;
import com.qaengine.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Modifying
    @Transactional
    @Query("update Answer a set a.accepted = false where a.questionId=:questionId")
    void revertAnswerAccepted(@Param("questionId") Long questionId);

}
