package com.qaengine.controllers;

import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class QuestionController {
    @Autowired
    QuestionRepository repository;

    @GetMapping("/questions")
    @CrossOrigin()
    protected List<Question> listQuestions() {
        return repository.findAll();
    }

    @PostMapping("/questions")
    @CrossOrigin()
    protected Question postQuestion(@RequestBody @Valid QuestionInput questionInput) {
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
            @RequestBody @Valid QuestionInput questionInput
    ) {
        Question question = getQuestion(id);
        HelperFunctions.copyProperties(question, questionInput);
        return repository.save(question);
    }

    @CrossOrigin()
    @PutMapping("questions/{id}/upvote")
    protected Question upvoteQuestion(
            @PathVariable Long id
    ) {
        Question question = getQuestion(id);
        question.setScore(question.getScore() + 1);
        return repository.save(question);
    }

    @CrossOrigin()
    @PutMapping("questions/{id}/downvote")
    protected Question downvoteQuestion(
            @PathVariable Long id
    ) {
        Question question = getQuestion(id);
        question.setScore(question.getScore() - 1);
        return repository.save(question);
    }

}
