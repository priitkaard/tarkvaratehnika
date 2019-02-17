package com.qaengine.services;

import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository repository;

    public Question getQuestion(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Question> question = repository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
