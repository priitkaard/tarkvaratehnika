package com.qaengine.services;

import com.qaengine.database.CommentRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment getCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new ResourceNotFoundException();
        }

    }

    public void deleteComment(Long id) {
        Comment comment = this.getCommentById(id);
        commentRepository.delete(comment);
    }
}
