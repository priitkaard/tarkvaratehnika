package com.qaengine.services;

import com.qaengine.database.AnswerRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    public Answer getAnswer(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
