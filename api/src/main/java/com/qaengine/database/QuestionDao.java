package com.qaengine.database;

import org.springframework.stereotype.Repository;

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
