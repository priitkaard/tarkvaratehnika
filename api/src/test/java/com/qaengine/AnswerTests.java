package com.qaengine;

import com.qaengine.controllers.AnswerController;
import com.qaengine.controllers.QuestionController;
import com.qaengine.controllers.UserController;
import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Answer;
import com.qaengine.models.DTO.AnswerDTO;
import com.qaengine.models.DTO.ApplicationUserDTO;
import com.qaengine.models.DTO.QuestionDTO;
import com.qaengine.models.Question;
import com.qaengine.services.*;
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
public class AnswerTests {


    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserController userController;
    @Autowired
    VoteService voteService;
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

    @Test
    public void addAnswerTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        assertEquals(question.getId(), answer.getQuestion().getId());
        assertEquals(answer.getText(), "test");
    }

    @Test
    public void getAnswerTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        assertEquals(answer.getId(), answerController.getAnswer(answer.getId()).getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteAnswerTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        answerController.deleteAnswer(answer.getId(), principal);
        answerController.getAnswer(answer.getId());
    }

    @Test
    public void updateAnswerTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        answerController.updateAnswer(answer.getId(), new AnswerDTO("tst2"), principal);
        Answer answer1 = answerController.getAnswer(answer.getId());
        assertEquals(answer.getId(), answer1.getId());
        assertEquals(answer1.getText(), "tst2");
    }

    @Test
    public void upvoteAnswerTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        answerController.upvoteAnswer(answer.getId(), principal);
        answer = answerController.getAnswer(answer.getId());
        assertEquals(answer.getScore().longValue(), 1L);
    }

    @Test
    public void downvoteAnswerTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        answerController.downvoteAnswer(answer.getId(), principal);
        answer = answerController.getAnswer(answer.getId());
        assertEquals(answer.getScore().longValue(), -1L);
    }
}
