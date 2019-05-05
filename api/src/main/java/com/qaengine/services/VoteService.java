package com.qaengine.services;

import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.database.VoteRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.models.Answer;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Question;
import com.qaengine.models.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private VoteRepository voteRepository;
    private QuestionService questionService;
    private QuestionRepository questionRepository;
    private AnswerService answerService;
    private AnswerRepository answerRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository, QuestionService questionService, QuestionRepository questionRepository, AnswerService answerService, AnswerRepository answerRepository) {
        this.voteRepository = voteRepository;
        this.questionService = questionService;
        this.questionRepository = questionRepository;
        this.answerService = answerService;
        this.answerRepository = answerRepository;
    }

    public Vote voteQuestion(Long questionId, ApplicationUser user, int relativeScore) {
        Question question = questionService.getQuestion(questionId);

        question.getVotes().forEach((Vote vote) -> {
            if (vote.getUser() == user) {
                throw new BadRequestException("You have already voted for this question");
            }
        });

        question.setScore(question.getScore() + relativeScore);
        questionRepository.save(question);

        Vote vote = Vote.builder()
                .question(question)
                .user(user)
                .relativeScore(relativeScore)
                .build();
        voteRepository.save(vote);
        return vote;
    }

    public Vote voteAnswer(Long answerId, ApplicationUser user, int relativeScore) {
        Answer answer = answerService.getAnswer(answerId);

        answer.getVotes().forEach((Vote vote) -> {
            if (vote.getUser() == user) {
                throw new BadRequestException("You have already voted for this question");
            }
        });

        answer.setScore(answer.getScore() + relativeScore);
        answerRepository.save(answer);

        Vote vote = Vote.builder()
                .answer(answer)
                .user(user)
                .relativeScore(relativeScore)
                .build();
        voteRepository.save(vote);
        return vote;
    }
}
