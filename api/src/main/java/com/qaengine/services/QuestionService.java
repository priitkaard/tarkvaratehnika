package com.qaengine.services;

import com.qaengine.database.QuestionRepository;
import com.qaengine.database.VoteRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Category;
import com.qaengine.models.DTO.QuestionDTO;
import com.qaengine.models.Question;
import com.qaengine.models.DTO.QuestionListDTO;
import com.qaengine.models.DTO.QuestionListElementDTO;
import com.qaengine.models.Vote;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private VoteRepository voteRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository,
                           CategoryService categoryService, VoteRepository voteRepository) {
        this.questionRepository = questionRepository;
        this.categoryService = categoryService;
        this.voteRepository = voteRepository;
    }

    public QuestionListDTO.QuestionListDTOOut listQuestions(QuestionListDTO.QuestionListDTOIn input) {
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

        Page<QuestionListElementDTO> questions;
        if (input.getCategoryId() != null) {
            questions = questionRepository.listQuestionsByCategory(input.getCategoryId(), input.getQuery(), pageRequest);
        } else {
            questions = questionRepository.listQuestions(input.getQuery(), pageRequest);
        }

        List<Long> questionIds = questions.getContent()
                .stream()
                .map(QuestionListElementDTO::getId)
                .collect(Collectors.toList());

        List<Vote> votes = voteRepository.findAllByQuestionIds(questionIds);

        QuestionListDTO dto = new QuestionListDTO();
        QuestionListDTO.QuestionListDTOOut list = dto.new QuestionListDTOOut();
        list.setTotalPages(questions.getTotalPages());
        list.setQuestions(questions.getContent()
                .stream()
                .peek(question -> {
                    question.setText(
                            Parser.unescapeEntities(Jsoup.parse(question.getText()).text(), false));
                    question.setVotes(votes.stream().filter(vote -> vote.getQuestion().getId() == question.getId()).collect(Collectors.toList()));
                })
                .collect(Collectors.toList()));
        return list;
    }

    public Question getQuestion(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<Question> question = questionRepository.findById(id);
        return question.orElseThrow(ResourceNotFoundException::new);
    }

    public void incrementViews(Long questionId) {
        Question question = getQuestion(questionId);
        question.setViews(question.getViews() + 1);
        questionRepository.save(question);
    }

    public List<Question> autoCompleteQuestion(String input) {
        return questionRepository.getSimilarQuestions(input, PageRequest.of(0, 5));
    }

    public Long getTotalQuestions(Optional<Long> categoryId) {
        return categoryId.map(id -> {
            Category category = this.categoryService.getCategoryById(categoryId.get());
            return questionRepository.countByCategory(category);
        }).orElse(questionRepository.count());
    }

    public Question saveQuestion(QuestionDTO questionInput, ApplicationUser user, Category category) {
        Question question = Question.builder()
                .title(questionInput.getTitle())
                .text(questionInput.getText())
                .category(category)
                .user(user)
                .build();
        return questionRepository.save(question);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question updateQuestion(Long id, QuestionDTO questionInput) {
        Question question = getQuestion(id);
        HelperFunctions.copyProperties(question, questionInput);
        Category category = categoryService.getCategoryById(questionInput.getCategoryId());
        question.setCategory(category);
        return questionRepository.save(question);
    }

    public Question upvoteQuestion(Long id) {
        Question question = getQuestion(id);
        question.setScore(question.getScore() + 1);
        return questionRepository.save(question);
    }

    public Question downvoteQuestion(Long id) {
        Question question = getQuestion(id);
        question.setScore(question.getScore() - 1);
        return questionRepository.save(question);
    }
}
