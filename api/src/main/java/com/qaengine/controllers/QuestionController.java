package com.qaengine.controllers;

import com.qaengine.database.QuestionRepository;
import com.qaengine.database.VoteRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Category;
import com.qaengine.models.Question;
import com.qaengine.models.Vote;
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
    private QuestionRepository questionRepository;
    private VoteRepository voteRepository;
    private QuestionService questionService;
    private CategoryService categoryService;
    private UserService userService;

    public QuestionController(
            QuestionRepository questionRepository,
            VoteRepository voteRepository, QuestionService questionService,
            CategoryService categoryService,
            UserService userService
    ) {
        this.questionRepository = questionRepository;
        this.voteRepository = voteRepository;
        this.questionService = questionService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/list")
    protected QuestionList listQuestions(@Valid QuestionListInput input) {
        return questionService.listQuestions(input);
    }

    @PostMapping
    protected Question postQuestion(
      @RequestBody @Valid QuestionInput questionInput,
      Principal principal
    ) {
        Category category = categoryService.getCategoryById(questionInput.getCategoryId());
        ApplicationUser user = userService.getUser(principal.getName());
        if (user == null) {
            throw new ResourceNotFoundException("Could not find user. Cannot create.");
        }
        Question question = Question.builder()
                .title(questionInput.getTitle())
                .text(questionInput.getText())
                .category(category)
                .user(user)
                .build();
        return questionRepository.save(question);
    }

    @GetMapping("/{id}")
    protected Question getQuestion(@PathVariable Long id) {
        questionService.incrementViews(id);
        return questionService.getQuestion(id);
    }

    @DeleteMapping("/{id}")
    protected Long deleteQuestion(@PathVariable Long id) {
        try {
            questionRepository.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping("/{id}")
    protected Question updateQuestion(
            @PathVariable Long id,
            @RequestBody @Valid QuestionInput questionInput
    ) {
        Question question = questionService.getQuestion(id);
        HelperFunctions.copyProperties(question, questionInput);
        return questionRepository.save(question);
    }

    @PutMapping("/{id}/upvote")
    protected Vote upvoteQuestion(
            @PathVariable Long id,
            Principal principal
    ) {
        ApplicationUser user = userService.getUser(principal.getName());
        Question question = questionService.getQuestion(id);

        question.getVotes().forEach((Vote vote) -> {
            if (vote.getUser() == user) {
                throw new BadRequestException("You have already voted for this question");
            }
        });

        question.setScore(question.getScore() + 1);
        questionRepository.save(question);

        Vote vote = Vote.builder()
                .question(question)
                .user(user)
                .relativeScore(1)
                .build();
        voteRepository.save(vote);
        return vote;
    }

    @PutMapping("/{id}/downvote")
    protected Vote downvoteQuestion(
            @PathVariable Long id,
            Principal principal
    ) {
        ApplicationUser user = userService.getUser(principal.getName());
        Question question = questionService.getQuestion(id);

        question.getVotes().forEach((Vote vote) -> {
            if (vote.getUser() == user) {
                throw new BadRequestException("You have already voted for this question");
            }
        });

        question.setScore(question.getScore() - 1);
        questionRepository.save(question);

        Vote vote = Vote.builder()
                .question(question)
                .user(user)
                .relativeScore(-1)
                .build();
        voteRepository.save(vote);
        return vote;
    }

    @GetMapping("/auto-complete")
    public List<Question> autoCompleteQuestion(@RequestParam String query) {
        return questionService.autoCompleteQuestion(query);
    }

}
