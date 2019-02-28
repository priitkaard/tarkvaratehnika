package com.qaengine.controllers;

import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Category;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionInput;
import com.qaengine.models.inputs.QuestionListInput;
import com.qaengine.models.outputs.QuestionListElement;
import com.qaengine.services.CategoryService;
import com.qaengine.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class QuestionController {
    private QuestionRepository questionRepository;
    private QuestionService questionService;
    private CategoryService categoryService;

    @Autowired
    public QuestionController(QuestionRepository questionRepository, QuestionService questionService, CategoryService categoryService) {
        this.questionRepository = questionRepository;
        this.questionService = questionService;
        this.categoryService = categoryService;
    }

    @GetMapping("/questions")
    @CrossOrigin()
    protected List<QuestionListElement> listQuestions(QuestionListInput input) {
        return questionService.listQuestions(input);
    }

    @PostMapping("/questions")
    @CrossOrigin()
    protected Question postQuestion(@RequestBody @Valid QuestionInput questionInput) {
        Category category = categoryService.getCategoryById(questionInput.getCategoryId());
        Question question = Question.builder()
                .title(questionInput.getTitle())
                .text(questionInput.getText())
                .category(category)
                .build();
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
        Question question = questionService.getQuestion(id);
        HelperFunctions.copyProperties(question, questionInput);
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @PutMapping("questions/{id}/upvote")
    protected Question upvoteQuestion(
            @PathVariable Long id
    ) {
        Question question = questionService.getQuestion(id);
        question.setScore(question.getScore() + 1);
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @PutMapping("questions/{id}/downvote")
    protected Question downvoteQuestion(
            @PathVariable Long id
    ) {
        Question question = questionService.getQuestion(id);
        question.setScore(question.getScore() - 1);
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @GetMapping("questions/auto-complete")
    public List<Question> autoCompleteQuestion(@RequestParam String query) {
        return questionService.autoCompleteQuestion(query);
    }

}
