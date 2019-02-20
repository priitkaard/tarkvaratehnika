package com.qaengine.database;

import com.qaengine.models.Question;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT q FROM Question q WHERE lower(q.title) LIKE lower(concat('%', ?1,'%'))")
    List<Question> getSimilarQuestions(String input, Pageable pageable);
}
