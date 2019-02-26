package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.Answer;
import org.springframework.data.repository.Repository;

public interface AnswerRepository extends Repository<Answer, Long> {
    void save(Answer answer);
}
