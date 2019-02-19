package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.topic.domain.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface TopicRepository extends Repository<Topic, Long> {
    @Query("select t from Topic t")
    List<Topic> listAll();
}
