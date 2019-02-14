package com.kaard.untitled.database;

import com.kaard.untitled.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    List<Question> findByText(String text);
}
