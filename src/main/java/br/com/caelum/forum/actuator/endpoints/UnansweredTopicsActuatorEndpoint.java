package br.com.caelum.forum.actuator.endpoints;

import br.com.caelum.forum.model.OpenTopicsByCategory;
import br.com.caelum.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Endpoint(id = "open-topics-by-category")
public class UnansweredTopicsActuatorEndpoint {

    @Autowired
    private TopicRepository topicRepository;

    @Bean
    @ReadOperation
    public List<OpenTopicsByCategory> execute() {
        return topicRepository.findOpenTopicsByCategory();
    }
}
