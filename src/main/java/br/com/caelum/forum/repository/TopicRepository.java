package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.topic.domain.Topic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicRepository extends Repository<Topic, Long>, JpaSpecificationExecutor<Topic> {
    @Query("select t from Topic t")
    List<Topic> listAll();

    List<Topic> findAll();

    @Query("select t from Topic t JOIN t.course c where c.name=:courseName")
    List<Topic> findTopics(@Param("courseName") String courseName);
}
