package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.topic.domain.Topic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

public interface TopicRepository extends Repository<Topic, Long>, JpaSpecificationExecutor<Topic> {

}
