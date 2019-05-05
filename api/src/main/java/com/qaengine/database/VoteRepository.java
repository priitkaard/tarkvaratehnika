package com.qaengine.database;

import com.qaengine.models.Question;
import com.qaengine.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT v FROM Vote v WHERE v.question.id IN :questionIds")
    List<Vote> findAllByQuestionIds(@Param("questionIds") Collection<Long> questionIds);
}
