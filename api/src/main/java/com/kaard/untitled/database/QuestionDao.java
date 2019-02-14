package com.kaard.untitled.database;

import com.kaard.untitled.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class QuestionDao {
    /*@Autowired
    JdbcTemplate template;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public Question insertQuestion(Question question) {
        if (question.getId() == null) {
            em.persist(question);
        } else {
            em.merge(question);
        }
        return question;
    }*/
}
