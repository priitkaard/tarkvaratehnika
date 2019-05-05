package com.qaengine.services;

import com.qaengine.database.CommentRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.*;
import com.qaengine.models.DTO.CommentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;
    private CategoryService categoryService;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          CategoryService categoryService) {
        this.commentRepository = commentRepository;
        this.categoryService = categoryService;
    }

    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElseThrow(ResourceNotFoundException::new);

    }

    public void deleteComment(Long id) {
        Comment comment = this.getCommentById(id);
        commentRepository.delete(comment);
    }

    public Long getTotalComments(Optional<Long> categoryId) {
        return categoryId.map(id -> {
            Category category = this.categoryService.getCategoryById(categoryId.get());
            return commentRepository.countByQuestionCategory(category)
                    + commentRepository.countByAnswerCategory(category);
        }).orElse(commentRepository.count());
    }

    public Comment commentQuestion(ApplicationUser user, Question question, CommentDTO commentInput) {
        Comment comment = new Comment();
        comment.setQuestion(question);
        comment.setUser(user);

        HelperFunctions.copyProperties(comment, commentInput);
        return commentRepository.save(comment);
    }

    public Comment commentAnswer(ApplicationUser user, Answer answer, CommentDTO commentInput) {
        Comment comment = new Comment();
        comment.setAnswer(answer);
        comment.setUser(user);

        HelperFunctions.copyProperties(comment, commentInput);
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, CommentDTO commentInput) {
        Comment comment = getCommentById(commentId);
        HelperFunctions.copyProperties(comment, commentInput);
        return commentRepository.save(comment);
    }

    public Comment upvoteComment(Long id) {
        Comment comment = getCommentById(id);
        comment.setScore(comment.getScore() + 1);
        return commentRepository.save(comment);
    }

    public Comment downvoteComment(Long id) {
        Comment comment = getCommentById(id);
        comment.setScore(comment.getScore() - 1);
        return commentRepository.save(comment);
    }
}
