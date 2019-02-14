package com.kaard.untitled.controllers;

import com.kaard.untitled.database.QuestionDao;
import com.kaard.untitled.database.QuestionRepository;
import com.kaard.untitled.models.Question;
import com.kaard.untitled.models.inputs.QuestionInput;
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
