package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.OpenTopicsByCategory;
import org.springframework.data.repository.Repository;

public interface OpenTopicsByCategoryRepository extends Repository<OpenTopicsByCategory, Long> {
    void saveAll(Iterable<OpenTopicsByCategory> topics);

}
