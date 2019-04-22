package com.qaengine.controllers;

import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Category;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionInput;
import com.qaengine.models.inputs.QuestionListInput;
import com.qaengine.models.outputs.QuestionList;
import com.qaengine.services.CategoryService;
import com.qaengine.services.QuestionService;
import com.qaengine.services.UserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;
    private CategoryService categoryService;
    private UserService userService;

    public QuestionController(
      QuestionService questionService,
      CategoryService categoryService,
      UserService userService
    ) {
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public QuestionList listQuestions(@Valid QuestionListInput input) {
        return questionService.listQuestions(input);
    }

    @PostMapping
    public Question postQuestion(
      @RequestBody @Valid QuestionInput questionInput,
      Principal principal
    ) {
        Category category = categoryService.getCategoryById(questionInput.getCategoryId());
        ApplicationUser user = userService.getUser(principal.getName());
        if (user == null) {
            throw new ResourceNotFoundException("Could not find user. Cannot create.");
        }
        return questionService.saveQuestion(questionInput, user, category);
    }

    @GetMapping("/{id}")
    public Question getQuestion(@PathVariable Long id) {
        questionService.incrementViews(id);
        return questionService.getQuestion(id);
    }

    @DeleteMapping("/{id}")
    public Long deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return id;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping("/{id}")
    public Question updateQuestion(
            @PathVariable Long id,
            @RequestBody @Valid QuestionInput questionInput
    ) {
        return questionService.updateQuestion(id, questionInput);
    }

    @PutMapping("/{id}/upvote")
    public Question upvoteQuestion(
            @PathVariable Long id
    ) {
        return questionService.upvoteQuestion(id);
    }

    @PutMapping("/{id}/downvote")
    public Question downvoteQuestion(
            @PathVariable Long id
    ) {
        return questionService.downvoteQuestion(id);
    }

    @GetMapping("/auto-complete")
    public List<Question> autoCompleteQuestion(@RequestParam String query) {
        return questionService.autoCompleteQuestion(query);
    }

}
