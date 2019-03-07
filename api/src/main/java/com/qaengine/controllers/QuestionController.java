package com.qaengine.controllers;

import com.qaengine.database.QuestionRepository;
import com.qaengine.database.UserRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Category;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionInput;
import com.qaengine.models.inputs.QuestionListInput;
import com.qaengine.models.outputs.QuestionList;
import com.qaengine.models.outputs.QuestionListElement;
import com.qaengine.services.CategoryService;
import com.qaengine.services.QuestionService;
import com.qaengine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class QuestionController {
    private QuestionRepository questionRepository;
    private QuestionService questionService;
    private CategoryService categoryService;
    private UserService userService;

    public QuestionController(
      QuestionRepository questionRepository,
      QuestionService questionService,
      CategoryService categoryService,
      UserService userService
    ) {
        this.questionRepository = questionRepository;
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/questions")
    @CrossOrigin()
    protected QuestionList listQuestions(@Valid QuestionListInput input) {
        return questionService.listQuestions(input);
    }

    @PostMapping()
    @CrossOrigin()
    protected Question postQuestion(
      @RequestBody @Valid QuestionInput questionInput,
      Principal principal
    ) {
        Category category = categoryService.getCategoryById(questionInput.getCategoryId());
        ApplicationUser user = userService.getUser(principal.getName());
        Question question = Question.builder()
                .title(questionInput.getTitle())
                .text(questionInput.getText())
                .category(category)
                .user(user)
                .build();
        return questionRepository.save(question);
    }

    @CrossOrigin("/questions")
    @GetMapping("{id}")
    protected Question getQuestion(@PathVariable Long id) {
        questionService.incrementViews(id);
        return questionService.getQuestion(id);
    }

    @CrossOrigin()
    @DeleteMapping("{id}")
    protected Long deleteQuestion(@PathVariable Long id) {
        try {
            questionRepository.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @CrossOrigin()
    @PutMapping("/questions{id}")
    protected Question updateQuestion(
            @PathVariable Long id,
            @RequestBody @Valid QuestionInput questionInput
    ) {
        Question question = questionService.getQuestion(id);
        HelperFunctions.copyProperties(question, questionInput);
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @PutMapping("/questions{id}/upvote")
    protected Question upvoteQuestion(
            @PathVariable Long id
    ) {
        Question question = questionService.getQuestion(id);
        question.setScore(question.getScore() + 1);
        return questionRepository.save(question);
    }

    @CrossOrigin()
    @PutMapping("/questions{id}/downvote")
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
