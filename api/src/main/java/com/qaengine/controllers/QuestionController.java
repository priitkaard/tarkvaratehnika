package com.qaengine.controllers;

import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Answer;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.AnswerInput;
import com.qaengine.models.inputs.QuestionInput;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class QuestionController {
    @Autowired
    QuestionRepository repository;

    @GetMapping("/questions")
    @CrossOrigin()
    public List<Question> listQuestions() {
        return repository.findAll();
    }

    @PostMapping("/questions")
    @CrossOrigin()
    public Question postQuestion(@RequestBody QuestionInput questionInput) {
        Question question = new Question();
        HelperFunctions.copyProperties(question, questionInput);
        return repository.save(question);
    }

    @CrossOrigin()
    @GetMapping("questions/{id}")
    protected Question getQuestion(@PathVariable Long id) {
        Optional<Question> question = repository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @CrossOrigin()
    @DeleteMapping("questions/{id}")
    protected Long deleteQuestion(@PathVariable Long id) {
        try {
            repository.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @CrossOrigin()
    @PutMapping("questions/{id}")
    protected Question updateQuestion(
            @PathVariable Long id,
            @RequestBody QuestionInput questionInput
    ) {
        Question question = getQuestion(id);
        HelperFunctions.copyProperties(question, questionInput);
        return repository.save(question);
    }

}
