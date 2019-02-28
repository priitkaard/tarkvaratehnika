package com.qaengine.services;

import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Category;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionListInput;
import com.qaengine.models.outputs.QuestionListElement;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    private CategoryService categoryService;

    private static final Map<String, String> SORT_OPTIONS = new HashMap<String, String>() {
        {
            put("SCORE_DESC", "score,desc");
            put("ANSWER_COUNT", "answer_count DESC");
            put("ANSWER_TIME", "");
        }
    };

    @Autowired
    public QuestionService(QuestionRepository questionRepository, CategoryService categoryService) {
        this.questionRepository = questionRepository;
        this.categoryService = categoryService;
    }

    private String mapSortKeyword(String sortKeyword) {
        String sort = sortKeyword;
        switch (sortKeyword) {
            case "comments":
                sort = "COUNT(c)";
                break;
            case "answers":
                sort = "COUNT(a)";
                break;
            case "last_comment":
                sort = "MAX(c.created)";
                break;
            case "last_answer":
                sort = "MAX(a.created)";
                break;
        }
        return sort;
    }

    public List<QuestionListElement> listQuestions(QuestionListInput input) {
        Sort.Direction direction = input.getDirection() != null && input.getDirection().toUpperCase().equals("DESC")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Sort sort;
        String sortInput = this.mapSortKeyword(input.getSort());
        if (sortInput.equals(input.getSort())) {
            sort = Sort.by(direction, sortInput);
        } else {
            sort = JpaSort.unsafe(direction, sortInput);
        }

        PageRequest pageRequest = PageRequest.of(input.getPage(), input.getLimit(), sort);
        List<QuestionListElement> questions;
        if (input.getCategoryId() != 0) {
            questions = questionRepository.listQuestionsByCategory(input.getCategoryId(), input.getQuery(), pageRequest);
        } else {
            questions = questionRepository.listQuestions(input.getQuery(), pageRequest);
        }

        return questions.stream()
                .peek(question -> question.setText(
                        Parser.unescapeEntities(Jsoup.parse(question.getText()).text(), false))
                )
                .collect(Collectors.toList());
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
