package com.qaengine.database;

import com.qaengine.models.Category;
import com.qaengine.models.Question;
import com.qaengine.models.DTO.QuestionListElementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {

    @Query(
            "SELECT new com.qaengine.models.DTO.QuestionListElementDTO(" +
            "       q.id, q.title, q.text, q.score, q.views, q.created, q.category, q.user," +
            "       COUNT(a), MAX(a.created), COUNT(c), MAX(c.created)) " +
            "FROM Question q " +
            "LEFT JOIN Vote v ON v.question = q " +
            "LEFT JOIN Comment c ON c.question = q " +
            "LEFT JOIN Answer a ON a.question = q " +
            "WHERE LOWER(q.title) LIKE CONCAT('%', LOWER(:query), '%') " +
            "GROUP BY q.id, q.title, q.text, q.score, q.created, q.category"
    )
    Page<QuestionListElementDTO> listQuestions(@Param("query") String query, Pageable pageable);

    @Query(
            "SELECT new com.qaengine.models.DTO.QuestionListElementDTO(" +
            "       q.id, q.title, q.text, q.score, q.views, q.created, q.category, q.user," +
            "       COUNT(a), MAX(a.created), COUNT(c), MAX(c.created)) " +
            "FROM Question q " +
            "LEFT JOIN Vote v ON v.question = q " +
            "LEFT JOIN Comment c ON c.question = q " +
            "LEFT JOIN Answer a ON a.question = q " +
            "WHERE LOWER(q.title) LIKE CONCAT('%', LOWER(:query), '%') AND q.category.id = :categoryId " +
            "GROUP BY q.id, q.title, q.text, q.score, q.created, q.category"
    )
    Page<QuestionListElementDTO> listQuestionsByCategory(@Param("categoryId") Long categoryId, @Param("query") String query, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Answer a set a.accepted = false where a.question = :question")
    void revertAnswerAccepted(Question question);

    @Query("SELECT q FROM Question q WHERE lower(q.title) LIKE lower(concat('%', ?1,'%'))")
    List<Question> getSimilarQuestions(String input, Pageable pageable);

    Long countByCategory(Category category);
}
