package com.qaengine;

import com.qaengine.controllers.QuestionController;
import com.qaengine.database.QuestionRepository;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionInput;
import static org.junit.Assert.*;
import com.qaengine.services.CategoryService;
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

	@Test
	public void contextLoads() {
	}

	@Test
	public void getQuestionFromController()
	{
		QuestionController questionController = new QuestionController(questionRepository, questionService,categoryService);
		QuestionInput questionInput = new QuestionInput("abc", "def", 1L);
		Question question = questionController.postQuestion(questionInput);
		Long questionId = question.getId();
		assertEquals(questionId, questionController.getQuestion(questionId).getId());
		//assertEquals(question.getAnswers(), questionController.getQuestion(questionId).getAnswers());
		//assertEquals(question.getCategory(), questionController.getQuestion(questionId).getCategory());
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
	public void getQuestionFromService()
	{
		QuestionService questionService = new QuestionService(questionRepository);
		QuestionController questionController = new QuestionController(questionRepository,questionService, categoryService);

		System.out.println(questionService.getQuestion(1L));
	}*/
}

