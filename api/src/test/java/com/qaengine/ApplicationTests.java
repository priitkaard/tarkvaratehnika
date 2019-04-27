package com.qaengine;

import com.qaengine.controllers.*;
import com.qaengine.database.AnswerRepository;
import com.qaengine.database.CommentRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.database.UserRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Answer;
import com.qaengine.models.Comment;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.*;

import static org.junit.Assert.*;

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

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

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
    UserRepository userRepository;
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
        userController.signUp(new ApplicationUserInput("name", "pw"));
    }

    // QuestionController
    @Test
    public void getQuestionFromController() {

        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        Long questionId = question.getId();
        assertEquals(questionId, questionController.getQuestion(questionId).getId());
        //assertEquals(question.getAnswers(), questionController.getQuestion(questionId).getAnswers());
        assertEquals(question.getCategory().getId().longValue(), questionController.getQuestion(questionId).getCategory().getId().longValue());
        assertEquals(question.getText(), questionController.getQuestion(questionId).getText());
        assertEquals(question.getTitle(), questionController.getQuestion(questionId).getTitle());
    }

    @Test
    public void createQuestion() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        try {
            questionController.postQuestion(questionInput, principal);
            Assert.assertTrue(Boolean.TRUE);
        } catch (Exception ex) {
            Assert.fail(ex.getMessage());
        }

    }

    @Test
    public void questionList() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 5L);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(new QuestionInput("def", "hji", 5L), principal);
        QuestionListInput questionListInput = new QuestionListInput(0, 10, "score", "DESC", "", 5L);
        assertEquals(questionController.listQuestions(questionListInput).getQuestions().size(), 2);

    }

    @Test
    public void upVote() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        assertEquals(question.getScore().longValue(), 0);
        questionController.upvoteQuestion(question.getId());
        assertEquals(questionController.getQuestion(question.getId()).getScore().longValue(), 1L);
    }

    @Test
    public void downVote() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        assertEquals(question.getScore().longValue(), 0);
        questionController.downvoteQuestion(question.getId());
        assertEquals(questionController.getQuestion(question.getId()).getScore().longValue(), -1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteQuestion() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        questionController.deleteQuestion(question.getId());
        questionController.getQuestion(question.getId());
    }

    @Test
    public void updateQuestion() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        questionController.updateQuestion(question.getId(), new QuestionInput("ddd", "gff", 2L));
        Question question2 = questionController.getQuestion(question.getId());
        assertEquals(question2.getText(), "ddd");
        assertEquals(question2.getTitle(), "gff");
        //UPDATE DOES NOT UPDATE CATEGORY
        assertEquals(question2.getCategory().getId().longValue(), 2L);
    }

    @Test
    public void autoCompleteTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        questionController.postQuestion(questionInput, principal);
        assertEquals(5, questionController.autoCompleteQuestion("d").size());
        questionController.postQuestion(new QuestionInput("aa", "l", 2L), principal);
        assertEquals(1, questionController.autoCompleteQuestion("l").size());
        assertEquals(0, questionController.autoCompleteQuestion("q").size());

    }

    //AnswerController

    @Test
    public void addAnswerTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        assertEquals(question.getId(), answer.getQuestion().getId());
        assertEquals(answer.getText(), "test");
    }

    @Test
    public void getAnswerTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        assertEquals(answer.getId(), answerController.getAnswer(answer.getId()).getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteAnswerTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        answerController.deleteAnswer(answer.getId());
        answerController.getAnswer(answer.getId());
    }

    @Test
    public void updateAnswerTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        answerController.updateAnswer(answer.getId(), new AnswerInput("tst2"));
        Answer answer1 = answerController.getAnswer(answer.getId());
        assertEquals(answer.getId(), answer1.getId());
        assertEquals(answer1.getText(), "tst2");
    }

    @Test
    public void upvoteAnswerTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        answerController.upvoteAnswer(answer.getId());
        answer = answerController.getAnswer(answer.getId());
        assertEquals(answer.getScore().longValue(), 1L);
    }

    @Test
    public void downvoteAnswerTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        answerController.downvoteAnswer(answer.getId());
        answer = answerController.getAnswer(answer.getId());
        assertEquals(answer.getScore().longValue(), -1L);
    }
/*
    @Test
    public void acceptAnswerTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        answerController.acceptAnswer(answer.getId());
        answer = answerController.getAnswer(answer.getId());
        assertTrue(answer.isAccepted());
    }
    */


    // CommentController TESTS
    @Test
    public void addCommentTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        CommentController commentController = new CommentController(commentRepository, questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentInput("test"), principal);
        Comment comment1 = commentController.commentQuestion(question.getId(), new CommentInput("test2"), principal);
        assertEquals(comment.getAnswer().getId(), answer.getId());
        assertEquals(comment1.getQuestion().getId(), question.getId());
        assertEquals(comment.getText(), "test");
    }

    @Test
    public void getCommentTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        CommentController commentController = new CommentController(commentRepository, questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentInput("test"), principal);
        assertEquals(comment.getText(), commentController.getComment(comment.getId()).getText());
    }

    @Test
    public void updateCommentTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        CommentController commentController = new CommentController(commentRepository, questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentInput("test"), principal);
        commentController.updateComment(comment.getId(), new CommentInput("new"));
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getText(), "new");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        CommentController commentController = new CommentController(commentRepository, questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentInput("test"), principal);
        commentController.deleteComment(comment.getId());
        commentController.getComment(comment.getId());
    }

    @Test
    public void upvoteCommentTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        CommentController commentController = new CommentController(commentRepository, questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentInput("test"), principal);
        commentController.upvoteComment(comment.getId());
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getScore().longValue(), 1);
    }

    @Test
    public void downvoteCommentTest() {
        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        CommentController commentController = new CommentController(commentRepository, questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentInput("test"), principal);
        commentController.downvoteComment(comment.getId());
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getScore().longValue(), -1);
    }

    //UserController TEST
    @Test(expected = BadRequestException.class)
    public void signUpWithSameNameTest()
    {
        userController.signUp(new ApplicationUserInput("test", "test"));
        userController.signUp(new ApplicationUserInput("test", "test"));
    }

    //CategoryController
    @Test
    public void categoryControllerTest() {
        CategoryController categoryController = new CategoryController(categoryService);
        assert(categoryController.listCategories().size()>0);

    }
    //statisticsController

    @Test
    public void getStatisticsTest()
    {
        StatisticsController statisticsController = new StatisticsController(questionService, userService, answerService, commentService);
        assertEquals(statisticsController.getStatistics(4L).getAnswers(), 0);
        assertEquals(statisticsController.getStatistics(4L).getComments(), 0);
        assertEquals(statisticsController.getStatistics(4L).getQuestions(), 0);

        QuestionController questionController = new QuestionController(questionRepository, questionService, categoryService, userService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 4L);
        Question question = questionController.postQuestion(questionInput, principal);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService, userService);
        Answer answer = answerController.answerQuestion(question.getId(), new AnswerInput("test"), principal);
        CommentController commentController = new CommentController(commentRepository, questionService, answerService, commentService, userService);
        Comment comment = commentController.commentAnswer(answer.getId(), new CommentInput("test"), principal);
        Comment comment1 = commentController.commentQuestion(question.getId(), new CommentInput("test2"), principal);
        Comment comment2 = commentController.commentAnswer(answer.getId(), new CommentInput("test3"), principal);

        assertEquals(statisticsController.getStatistics(4L).getQuestions(), 1);
        assertEquals(statisticsController.getStatistics(4L).getAnswers(), 1);
        //Right now does not count question comments
        assertEquals(statisticsController.getStatistics(4L).getComments(), 3);

    }
}

