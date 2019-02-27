package br.com.caelum.forum.task;

import br.com.caelum.forum.model.OpenTopicsByCategory;
import br.com.caelum.forum.repository.OpenTopicsByCategoryRepository;
import br.com.caelum.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterUnansweredTopicsTask {
    private TopicRepository topicRepository;
    private OpenTopicsByCategoryRepository openTopicsByCategoryRepository;

    @Autowired
    public RegisterUnansweredTopicsTask(TopicRepository topicRepository, OpenTopicsByCategoryRepository openTopicsByCategoryRepository) {
        this.topicRepository = topicRepository;
        this.openTopicsByCategoryRepository = openTopicsByCategoryRepository;
    }

    @Scheduled(cron="*/10 * * * * *")
    public void execute() {
        List<OpenTopicsByCategory> topics = topicRepository.findOpenTopicsByCategory();
        this.openTopicsByCategoryRepository.saveAll(topics);
    }

}
