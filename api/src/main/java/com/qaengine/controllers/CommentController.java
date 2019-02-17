package com.qaengine.controllers;

import com.qaengine.database.AnswerRepository;
import com.qaengine.database.CommentRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Answer;
import com.qaengine.models.Comment;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.AnswerInput;
import com.qaengine.models.inputs.CommentInput;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CommentController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    QuestionController questionController;
    @Autowired
    AnswerController answerController;

    @CrossOrigin()
    @PostMapping("questions/{questionId}/comments")
    protected Comment addCommentToQuestion(
            @PathVariable Long questionId,
            @RequestBody @Valid CommentInput commentInput
    ) {
        Question question = questionController.getQuestion(questionId);
        Comment comment = new Comment();
        comment.setQuestionId(questionId);

        HelperFunctions.copyProperties(comment, commentInput);
        comment = commentRepository.save(comment);
        question.getComments().add(comment);
        questionRepository.save(question);
        return comment;
    }

    @CrossOrigin()
    @PostMapping("answers/{answerId}/comments")
    protected Comment addCommentToAnswer(
            @PathVariable Long answerId,
            @RequestBody @Valid CommentInput commentInput
    ) {
        Answer answer = answerController.getAnswer(answerId);
        Comment comment = new Comment();
        comment.setAnswerid(answerId);
        HelperFunctions.copyProperties(comment, commentInput);
        comment = commentRepository.save(comment);
        answer.getComments().add(comment);
        answerRepository.save(answer);
        return comment;
    }

    @CrossOrigin()
    @GetMapping("comments/{id}")
    protected Comment getComment(@PathVariable Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @CrossOrigin()
    @PutMapping("comments/{commentId}")
    protected Comment updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentInput commentInput
    ) {
        Comment comment = getComment(commentId);
        HelperFunctions.copyProperties(comment, commentInput);
        return commentRepository.save(comment);
    }

    @CrossOrigin()
    @DeleteMapping("comments/{commentId}")
    protected Long deleteComment(
            @PathVariable Long commentId
    ) {
        Comment comment = getComment(commentId);
        if (comment.getAnswerid() != null) {
            Answer answer = answerController.getAnswer(comment.getAnswerid());
            answer.getComments().remove(comment);
            answerRepository.save(answer);
        } else if (comment.getQuestionId() != null) {
            Question question = questionController.getQuestion(comment.getQuestionId());
            question.getComments().remove(comment);
            questionRepository.save(question);
        }
        return commentId;
    }


    @CrossOrigin()
    @PutMapping("comments/{id}/upvote")
    protected Comment upvoteComment(
            @PathVariable Long id
    ) {
        Comment comment = getComment(id);
        comment.setScore(comment.getScore() + 1);
        return commentRepository.save(comment);
    }

    @CrossOrigin()
    @PutMapping("comments/{id}/downvote")
    protected Comment downvoteComment(
            @PathVariable Long id
    ) {
        Comment comment = getComment(id);
        comment.setScore(comment.getScore() - 1);
        return commentRepository.save(comment);
    }
}
