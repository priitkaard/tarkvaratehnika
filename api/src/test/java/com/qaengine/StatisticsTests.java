package com.qaengine;

import com.qaengine.controllers.*;
import com.qaengine.database.AnswerRepository;
import com.qaengine.database.CommentRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.models.Answer;
import com.qaengine.models.Comment;
import com.qaengine.models.DTO.AnswerDTO;
import com.qaengine.models.DTO.CommentDTO;
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
public class StatisticsTests {
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
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserService userService;
    @Autowired
    VoteService voteService;
    Principal principal = new Principal() {
        @Override
        public String getName() {
            return "name";
        }
    };
    @Test
    public void getStatisticsTest()
    {
        StatisticsController statisticsController = new StatisticsController(questionService, userService, answerService, commentService);
        assertEquals(statisticsController.getStatistics(4L).getAnswers(), 0);
        assertEquals(statisticsController.getStatistics(4L).getComments(), 0);
        assertEquals(statisticsController.getStatistics(4L).getQuestions(), 0);

        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 4L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        CommentController commentController = new CommentController(questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentDTO("test"), principal);
        Comment comment1 = commentController.commentQuestion(question.getId(), new CommentDTO("test2"), principal);
        Comment comment2 = commentController.commentAnswer(answer.getId(), new CommentDTO("test3"), principal);

        assertEquals(statisticsController.getStatistics(4L).getQuestions(), 1);
        assertEquals(statisticsController.getStatistics(4L).getAnswers(), 1);
        assertEquals(statisticsController.getStatistics(4L).getComments(), 3);

    }
}
