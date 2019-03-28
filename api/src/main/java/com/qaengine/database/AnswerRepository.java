package com.qaengine.database;

import com.qaengine.models.Answer;
import com.qaengine.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query("SELECT count(a) FROM Answer a WHERE a.question.category = :category")
    Long countByCategory(@Param("category") Category category);
}
