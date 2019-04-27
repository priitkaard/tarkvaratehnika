package com.qaengine.services;

import com.qaengine.database.QuestionRepository;
import com.qaengine.database.VoteRepository;
import com.qaengine.exceptions.BadRequestException;
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

    @Autowired
    public VoteService(VoteRepository voteRepository, QuestionService questionService, QuestionRepository questionRepository) {
        this.voteRepository = voteRepository;
        this.questionService = questionService;
        this.questionRepository = questionRepository;
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
}
