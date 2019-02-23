package com.qaengine.controllers;

import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionInput;
import com.qaengine.services.AnswerService;
import com.qaengine.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;

    @GetMapping("/questions")
    @CrossOrigin()
    protected List<Question> listQuestions(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "limit") Integer limit) {
        return questionService.listQuestions(page, limit);
    }

    @PostMapping("/questions")
    @CrossOrigin()
    protected Question postQuestion(@RequestBody @Valid QuestionInput questionInput) {
        Question question = new Question();
        HelperFunctions.copyProperties(question, questionInput);
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @GetMapping("questions/{id}")
    protected Question getQuestion(@PathVariable Long id) {
        return questionService.getQuestion(id);
    }

    @CrossOrigin()
    @DeleteMapping("questions/{id}")
    protected Long deleteQuestion(@PathVariable Long id) {
        try {
            questionRepository.deleteById(id);
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
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @PutMapping("questions/{id}/upvote")
    protected Question upvoteQuestion(
            @PathVariable Long id
    ) {
        Question question = getQuestion(id);
        question.setScore(question.getScore() + 1);
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @PutMapping("questions/{id}/downvote")
    protected Question downvoteQuestion(
            @PathVariable Long id
    ) {
        Question question = getQuestion(id);
        question.setScore(question.getScore() - 1);
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @GetMapping("questions/auto-complete")
    public List<Question> autoCompleteQuestion(@RequestParam String query) {
        return questionService.autoCompleteQuestion(query);
    }

}
