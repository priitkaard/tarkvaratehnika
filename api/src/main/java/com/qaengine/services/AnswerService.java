package com.qaengine.services;

import com.qaengine.database.AnswerRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Answer;
import com.qaengine.models.Category;
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

    public Answer getAnswer(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public Long getTotalAnswers(Optional<Long> categoryId) {
        if (categoryId.isPresent()) {
            Category category = this.categoryService.getCategoryById(categoryId.get());
            return answerRepository.countByCategory(category);
        }
        return answerRepository.count();
    }
}
