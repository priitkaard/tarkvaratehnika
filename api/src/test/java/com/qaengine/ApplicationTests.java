package com.qaengine;

import com.qaengine.controllers.AnswerController;
import com.qaengine.controllers.QuestionController;
import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.AnswerInput;
import com.qaengine.models.inputs.QuestionInput;
import static org.junit.Assert.*;

import com.qaengine.models.inputs.QuestionListInput;
import com.qaengine.services.AnswerService;
import com.qaengine.services.CategoryService;
import com.qaengine.services.QuestionService;
import org.hibernate.Hibernate;
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
        answerController.addAnswer(question.getId(), new AnswerInput("test"));
        Question question2 = questionController.getQuestion(question.getId());
        System.out.println(question2.getTitle());
        Hibernate.initialize(question2.getAnswers());
        assertEquals(question2.getAnswers().size(), 1);

        //assertEquals(question.getAnswers().get(0).getText().toString(), "test");
    }
}

