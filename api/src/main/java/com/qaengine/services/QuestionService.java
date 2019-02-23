package com.qaengine.services;

import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.models.Question;
import com.qaengine.models.outputs.QuestionListElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private static final Pattern HTML_TAGS_PATTERN = Pattern.compile("<.+?>");

    private QuestionRepository repository;

    @Autowired
    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<QuestionListElement> listQuestions(int page, int limit) {
        List<QuestionListElement> questions = repository.listQuestions(PageRequest.of(page, limit));
        return questions.stream().peek(question -> {
            Matcher matcher = HTML_TAGS_PATTERN.matcher(question.getText());
            question.setText(matcher.replaceAll(""));
        }).collect(Collectors.toList());
    }

    public Question getQuestion(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Question> question = repository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    public List<Question> autoCompleteQuestion(String input) {
        return repository.getSimilarQuestions(input, PageRequest.of(0, 5));
    }
}
