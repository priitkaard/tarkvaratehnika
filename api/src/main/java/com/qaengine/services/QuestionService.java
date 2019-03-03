package com.qaengine.services;

import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionListInput;
import com.qaengine.models.outputs.QuestionListElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionListElement> listQuestions(QuestionListInput input) {
        Map<String, Sort.Direction> sortDirections = new HashMap<String, Sort.Direction>() {{
            put("DESC", Sort.Direction.DESC);
            put("ASC", Sort.Direction.ASC);
        }};

        if (!sortDirections.keySet().contains(input.getDirection().toUpperCase())) {
            throw new BadRequestException("Invalid sort direction!");
        }

        Map<String, String> sortOptions = new HashMap<String, String>() {{
            put("score", "score");
            put("created", "created");
            put("answers", "COUNT(a)");
            put("last_answer", "COALESCE(MAX(a.created), q.created)");
            put("comments", "COUNT(c)");
            put("last_comment", "COALESCE(MAX(c.created), q.created)");
        }};

        if (!sortOptions.keySet().contains(input.getSort())) {
            throw new BadRequestException("Invalid sort argument!");
        }

        Sort sort = JpaSort.unsafe(
                sortDirections.get(input.getDirection().toUpperCase()),
                sortOptions.get(input.getSort())
        );
        Pageable pageRequest = PageRequest.of(input.getPage(), input.getLimit(), sort);

        if (input.getCategoryId() != null) {
            return questionRepository.listQuestionsByCategory(input.getCategoryId(), input.getQuery(), pageRequest);
        }
        return questionRepository.listQuestions(input.getQuery(), pageRequest);
    }

    public Question getQuestion(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public List<Question> autoCompleteQuestion(String input) {
        return questionRepository.getSimilarQuestions(input, PageRequest.of(0, 5));
    }
}
