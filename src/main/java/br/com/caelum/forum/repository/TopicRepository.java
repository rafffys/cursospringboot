package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.OpenTopicsByCategory;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.model.topic.domain.Topic;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TopicRepository extends Repository<Topic, Long>, JpaSpecificationExecutor<Topic> {
    @Query("select COUNT(t) from Topic t " +
            "JOIN t.course c " +
            "JOIN c.subcategory sc " +
            "JOIN sc.category c " +
            "WHERE c.id=:idCategory")
    int getTotalTopicsByIdCategory(@Param("idCategory") Long idCategory);

    @Query("select COUNT(t) from Topic t " +
            "JOIN t.course c " +
            "JOIN c.subcategory sc " +
            "JOIN sc.category c " +
            "WHERE c.id=:idCategory AND t.creationInstant >= :lastWeekInstant")
    int getTotalTopicsFromLastWeekByIdCategory(@Param("idCategory") Long idCategory,
                                               @Param("lastWeekInstant") Instant lastWeekInstant);

    @Query("select COUNT(t) from Topic t " +
            "JOIN t.course c " +
            "JOIN c.subcategory sc " +
            "JOIN sc.category c " +
            "WHERE c.id=:idCategory AND t.status = br.com.caelum.forum.model.topic.domain.TopicStatus.NOT_ANSWERED")
    int getTotalUnansweredTopicsByIdCategory(@Param("idCategory") Long idCategory);

    List<Topic> findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(User loggedUser, Instant oneHourAgo);

    Optional<Topic> findById(Long id);

    void save(Topic topic);

    @Query("select new br.com.caelum.forum.model.OpenTopicsByCategory(" +
            "t.course.subcategory.category.name as categoryName, " +
            "count(t) as topicCount, " +
            "now() as instant) from Topic t " +
            "where t.status = 'NOT_ANSWERED' " +
            "group by t.course.subcategory.category")
    List<OpenTopicsByCategory> findOpenTopicsByCategory();
}
