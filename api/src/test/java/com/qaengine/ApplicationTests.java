package com.qaengine;

import com.qaengine.controllers.AnswerController;
import com.qaengine.controllers.CommentController;
import com.qaengine.controllers.QuestionController;
import com.qaengine.controllers.UserController;
import com.qaengine.database.AnswerRepository;
import com.qaengine.database.CommentRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Answer;
import com.qaengine.models.Comment;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.*;
import static org.junit.Assert.*;
import com.qaengine.services.AnswerService;
import com.qaengine.services.CategoryService;
import com.qaengine.services.CommentService;
import com.qaengine.services.QuestionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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


	@Test
	public void contextLoads() {
	}

	// QuestionController
	@Test
	public void getQuestionFromController()
	{
		QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
		QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
		Question question = questionController.postQuestion(questionInput);
		Long questionId = question.getId();
		assertEquals(questionId, questionController.getQuestion(questionId).getId());
		//assertEquals(question.getAnswers(), questionController.getQuestion(questionId).getAnswers());
		assertEquals(question.getCategory().getId().longValue(), questionController.getQuestion(questionId).getCategory().getId().longValue());
		assertEquals(question.getText(), questionController.getQuestion(questionId).getText());
		assertEquals(question.getTitle(), questionController.getQuestion(questionId).getTitle());
	}

	@Test
	public void createQuestion()
	{
		QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
		QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
		try {
			questionController.postQuestion(questionInput);
			Assert.assertTrue(Boolean.TRUE);
		}
		catch(Exception ex)
		{
			Assert.fail(ex.getMessage());
		}

	}
	/*@Test
    public void questionList()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        questionController.postQuestion(questionInput);
        questionController.postQuestion(new QuestionInput("def", "hji", 2L));
        //assertEquals(questionController.listQuestions(new QuestionListInput()).getQuestions().size(),2);

    }*/

    @Test
    public void upVote()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        assertEquals(question.getScore().longValue(), 0);
        questionController.upvoteQuestion(question.getId());
        assertEquals(questionController.getQuestion(question.getId()).getScore().longValue(), 1L);
    }
    @Test
    public void downVote()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        assertEquals(question.getScore().longValue(), 0);
        questionController.downvoteQuestion(question.getId());
        assertEquals(questionController.getQuestion(question.getId()).getScore().longValue(), -1L);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteQuestion()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        questionController.deleteQuestion(question.getId());
        questionController.getQuestion(question.getId());
    }

    @Test
    public void updateQuestion(){
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        questionController.updateQuestion(question.getId(), new QuestionInput("ddd", "gff", 2L));
        Question question2 = questionController.getQuestion(question.getId());
        assertEquals(question2.getText(), "ddd");
        assertEquals(question2.getTitle(), "gff");
        //UPDATE DOES NOT UPDATE CATEGORY
        assertEquals(question2.getCategory().getId().longValue(), 2L);
    }

	/*@Test
	public void getQuestionFromService()
	{
		QuestionService questionService = new QuestionService(questionRepository);
		QuestionController questionController = new QuestionController(questionRepository,questionService, categoryService);

		System.out.println(questionService.getQuestion(1L));
	}*/

	//AnswerController

    @Test
    public void addAnswerTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        assertEquals(question.getId(), answer.getQuestion().getId());
        assertEquals(answer.getText(), "test");
    }

    @Test
    public void getAnswerTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        assertEquals(answer.getId(), answerController.getAnswer(answer.getId()).getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteAnswerTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        answerController.deleteAnswer(answer.getId());
        answerController.getAnswer(answer.getId());
    }

    @Test
    public void updateAnswerTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        answerController.updateAnswer(answer.getId(), new AnswerInput("tst2"));
        Answer answer1 = answerController.getAnswer(answer.getId());
        assertEquals(answer.getId(), answer1.getId());
        assertEquals(answer1.getText(), "tst2");
    }

    @Test
    public void upvoteAnswerTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        answerController.upvoteAnswer(answer.getId());
        answer = answerController.getAnswer(answer.getId());
        assertEquals(answer.getScore().longValue(), 1L);
    }

    @Test
    public void downvoteAnswerTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        answerController.downvoteAnswer(answer.getId());
        answer = answerController.getAnswer(answer.getId());
        assertEquals(answer.getScore().longValue(), -1L);
    }

    /*@Test
    public void acceptAnswerTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        answerController.acceptAnswer(answer.getId());
        answer = answerController.getAnswer(answer.getId());
        assertTrue(answer.isAccepted());
    }*/


    // CommentController TESTS
    @Test
    public void addCommentTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        CommentController commentController = new CommentController(commentRepository,questionService, answerService, commentService);
        Comment comment = commentController.addCommentToAnswer(answer.getId(), new CommentInput("test"));
        Comment comment1 = commentController.addCommentToQuestion(question.getId(), new CommentInput("test2"));
        assertEquals(comment.getAnswer().getId(), answer.getId());
        assertEquals(comment1.getQuestion().getId(), question.getId());
        assertEquals(comment.getText(), "test");
    }

    @Test
    public void getCommentTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        CommentController commentController = new CommentController(commentRepository,questionService, answerService, commentService);
        Comment comment = commentController.addCommentToAnswer(answer.getId(), new CommentInput("test"));
        assertEquals(comment.getText(), commentController.getComment(comment.getId()).getText());
    }

    @Test
    public void updateCommentTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        CommentController commentController = new CommentController(commentRepository,questionService, answerService, commentService);
        Comment comment = commentController.addCommentToAnswer(answer.getId(), new CommentInput("test"));
        commentController.updateComment(comment.getId(), new CommentInput("new"));
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getText(), "new");
    }

    @Test(expected = ResourceNotFoundException.class)
    public void deleteCommentTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        CommentController commentController = new CommentController(commentRepository,questionService, answerService, commentService);
        Comment comment = commentController.addCommentToAnswer(answer.getId(), new CommentInput("test"));
        commentController.deleteComment(comment.getId());
        commentController.getComment(comment.getId());
    }

    @Test
    public void upvoteCommentTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        CommentController commentController = new CommentController(commentRepository,questionService, answerService, commentService);
        Comment comment = commentController.addCommentToAnswer(answer.getId(), new CommentInput("test"));
        commentController.upvoteComment(comment.getId());
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getScore().longValue(), 1);
    }

    @Test
    public void downvoteCommentTest()
    {
        QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
        QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
        Question question = questionController.postQuestion(questionInput);
        AnswerController answerController = new AnswerController(questionRepository, answerRepository, answerService, questionService);
        Answer answer = answerController.addAnswer(question.getId(), new AnswerInput("test"));
        CommentController commentController = new CommentController(commentRepository,questionService, answerService, commentService);
        Comment comment = commentController.addCommentToAnswer(answer.getId(), new CommentInput("test"));
        commentController.downvoteComment(comment.getId());
        comment = commentController.getComment(comment.getId());
        assertEquals(comment.getScore().longValue(), -1);
    }

    //UserController TEST
    //Can not test that, @autowired
    /*@Test()
    public void signUpWithSameNameTest()
    {
        UserController userController = new UserController();
        System.out.println(new ApplicationUserInput("te", "te").getUsername());
        userController.signUp(new ApplicationUserInput("test", "test"));
        userController.signUp(new ApplicationUserInput("test", "test"));
    }*/
}

