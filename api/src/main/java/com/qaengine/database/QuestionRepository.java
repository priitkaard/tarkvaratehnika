package com.qaengine.database;

import com.qaengine.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    List<Question> findByText(String text);
}
