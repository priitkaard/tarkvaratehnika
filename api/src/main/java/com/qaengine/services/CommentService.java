package com.qaengine.services;

import com.qaengine.database.CommentRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Category;
import com.qaengine.models.Comment;
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
}
