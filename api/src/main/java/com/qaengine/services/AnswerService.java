package com.qaengine.services;

import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Answer;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Category;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.AnswerInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    QuestionService questionService;

    public Answer getAnswer(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Answer> answer = answerRepository.findById(id);
        return answer.orElseThrow(ResourceNotFoundException::new);
    }

    public Long getTotalAnswers(Optional<Long> categoryId) {
        return categoryId.map(id -> {
            Category category = this.categoryService.getCategoryById(categoryId.get());
            return answerRepository.countByCategory(category);
        }).orElse(answerRepository.count());
    }

    public Answer saveAnswer(ApplicationUser user, Question question, AnswerInput answerInput) {
        Answer answer = new Answer();
        HelperFunctions.copyProperties(answer, answerInput);
        answer.setQuestion(question);
        answer.setUser(user);

        return answerRepository.save(answer);
    }

    public Long deleteAnswer(Long id) {
        Answer answer = getAnswer(id);
        answerRepository.delete(answer);
        return id;
    }

    public Answer updateAnswer(Long id, AnswerInput answerInput) {
        Answer answer = getAnswer(id);
        HelperFunctions.copyProperties(answer, answerInput);
        return answerRepository.save(answer);
    }

    public Answer upvoteAnswer(Long id) {
        Answer answer = getAnswer(id);
        answer.setScore(answer.getScore() + 1);
        return answerRepository.save(answer);
    }

    public Answer downvoteAnswer(Long id) {
        Answer answer = getAnswer(id);
        answer.setScore(answer.getScore() - 1);
        return answerRepository.save(answer);
    }

    public Answer acceptAnswer(Long answerId) {
        Answer answer = getAnswer(answerId);

        Question question = answer.getQuestion();
        questionRepository.revertAnswerAccepted(question);

        answer.setAccepted(true);
        return answerRepository.save(answer);
    }

    public Question revertAnswerAccepted(Long questionId) {
        Question question = questionService.getQuestion(questionId);
        questionRepository.revertAnswerAccepted(question);
        return question;
    }
}
