package com.qaengine;

import com.qaengine.controllers.QuestionController;
import com.qaengine.controllers.UserController;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.DTO.ApplicationUserDTO;
import com.qaengine.models.DTO.QuestionDTO;
import com.qaengine.models.DTO.QuestionListDTOIn;
import com.qaengine.models.Question;
import com.qaengine.services.*;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionTests {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    VoteService voteService;
    @Autowired
    UserController userController;

    Principal principal = new Principal() {
        @Override
        public String getName() {
            return "name";
        }
    };

    @Test
    public void aaSignUpUserForTests() {
        userController.signUp(new ApplicationUserDTO("name", "pw"));
    }

    // QuestionController
    @Test
    public void getQuestionFromController() {

        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        Long questionId = question.getId();
        assertEquals(questionId, questionController.getQuestion(questionId).getId());
        assertEquals(question.getCategory().getId().longValue(), questionController.getQuestion(questionId).getCategory().getId().longValue());
        assertEquals(question.getText(), questionController.getQuestion(questionId).getText());
        assertEquals(question.getTitle(), questionController.getQuestion(questionId).getTitle());
    }

    @Test
    public void createQuestion() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        try {
            questionController.postQuestion(questionInput, principal);
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }

    }

    @Test
    public void questionList() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionListDTOIn questionListInput = new QuestionListDTOIn(0, 10, "score", "DESC", "", 5L);
        int previousSize = questionController.listQuestions(questionListInput).getQuestions().size();
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 5L);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(new QuestionDTO("def", "hji", 5L), principal);

        assertEquals(questionController.listQuestions(questionListInput).getQuestions().size()-previousSize, 2);

    }

    @Test
    public void upVote() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        assertEquals(question.getScore().longValue(), 0);
        questionController.upvoteQuestion(question.getId(), principal);
        assertEquals(questionController.getQuestion(question.getId()).getScore().longValue(), 1L);
    }

    @Test
    public void downVote() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        assertEquals(question.getScore().longValue(), 0);
        questionController.downvoteQuestion(question.getId(), principal);
        assertEquals(questionController.getQuestion(question.getId()).getScore().longValue(), -1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteQuestion() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        questionController.deleteQuestion(question.getId(), principal);
        questionController.getQuestion(question.getId());
    }

    @Test
    public void updateQuestion() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        questionController.updateQuestion(question.getId(), new QuestionDTO("ddd", "gff", 2L), principal);
        Question question2 = questionController.getQuestion(question.getId());
        assertEquals(question2.getText(), "ddd");
        assertEquals(question2.getTitle(), "gff");
        //UPDATE DOES NOT UPDATE CATEGORY
        assertEquals(question2.getCategory().getId().longValue(), 2L);
    }

    @Test
    public void autoCompleteTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        assertEquals(5, questionController.autoCompleteQuestion("d").size());
        questionController.postQuestion(new QuestionDTO("aa", "l", 2L), principal);
        assertEquals(1, questionController.autoCompleteQuestion("l").size());
        assertEquals(0, questionController.autoCompleteQuestion("q").size());

    }
}
