package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.topic.domain.Topic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TopicRepositoryTests {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private TopicRepository topicRepository;

    @Test
    public void shouldSaveATopic() {
        Topic topic = new Topic("Descrição do Tópico", "Conteúdo do Tópico", null, null);
        Topic persistedTopic = this.topicRepository.save(topic);
        Topic foundTopic = this.testEntityManager.find(Topic.class, persistedTopic.getId());
        assertThat(foundTopic).isNotNull();

        assertThat(foundTopic.getShortDescription()).isEqualTo(persistedTopic.getShortDescription());
    }
}
