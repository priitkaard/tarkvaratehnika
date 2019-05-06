package com.qaengine;

import com.qaengine.controllers.AnswerController;
import com.qaengine.controllers.CommentController;
import com.qaengine.controllers.QuestionController;
import com.qaengine.controllers.UserController;
import com.qaengine.database.AnswerRepository;
import com.qaengine.database.CommentRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Answer;
import com.qaengine.models.Comment;
import com.qaengine.models.DTO.AnswerDTO;
import com.qaengine.models.DTO.ApplicationUserDTO;
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
public class CommentTests {

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

    // CommentController TESTS
    @Test
    public void addCommentTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        CommentController commentController = new CommentController(questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentDTO("test"), principal);
        Comment comment1 = commentController.commentQuestion(question.getId(), new CommentDTO("test2"), principal);
        assertEquals(comment.getAnswer().getId(), answer.getId());
        assertEquals(comment1.getQuestion().getId(), question.getId());
        assertEquals(comment.getText(), "test");
    }

    @Test
    public void getCommentTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        CommentController commentController = new CommentController(questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentDTO("test"), principal);
        assertEquals(comment.getText(), commentController.getComment(comment.getId()).getText());
    }

    @Test
    public void updateCommentTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        CommentController commentController = new CommentController(questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentDTO("test"), principal);
        commentController.updateComment(comment.getId(), new CommentDTO("new"), principal);
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getText(), "new");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        CommentController commentController = new CommentController(questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentDTO("test"), principal);
        commentController.deleteComment(comment.getId(), principal);
        commentController.getComment(comment.getId());
    }

    @Test
    public void upvoteCommentTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        CommentController commentController = new CommentController(questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentDTO("test"), principal);
        commentController.upvoteComment(comment.getId());
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getScore().longValue(), 1);
    }

    @Test
    public void downvoteCommentTest() {
        QuestionController questionController = new QuestionController(questionService, categoryService, userService, voteService);
        QuestionDTO questionInput = new QuestionDTO("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(answerService, questionService, userService, voteService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerDTO("test"), principal);
        CommentController commentController = new CommentController(questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentDTO("test"), principal);
        commentController.downvoteComment(comment.getId());
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getScore().longValue(), -1);
    }

}
