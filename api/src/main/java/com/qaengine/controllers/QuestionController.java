package com.qaengine.controllers;

import com.qaengine.database.QuestionRepository;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.QuestionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {
    @Autowired
    QuestionRepository repository;

    @GetMapping("/questions")
    @CrossOrigin()
    public List<Question> getQuestions() {
        return null;
    }

    @PostMapping("/question")
    @CrossOrigin()
    public Question postQuestion(@RequestBody QuestionInput questionInput) {
        Question question = new Question(questionInput.getText());
        return repository.save(question);
    }


}
