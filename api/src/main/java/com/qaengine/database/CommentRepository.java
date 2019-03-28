package com.qaengine.database;

import com.qaengine.models.Category;
import com.qaengine.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT count(c) FROM Comment c " +
            "WHERE c.question.category = :category")
    Long countByQuestionCategory(@Param("category") Category category);

    @Query("SELECT count(c) FROM Comment c " +
            "WHERE c.answer.question.category = :category")
    Long countByAnswerCategory(@Param("category") Category category);
}
