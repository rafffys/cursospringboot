package br.com.caelum.forum.model;

import br.com.caelum.forum.model.topic.domain.Topic;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class PossibleSpam {
    private List<Topic> topicList;

    public PossibleSpam(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public boolean hasTopicLimitExceeded() {
        return this.topicList.size() >= 4;
    }

    public long minutesToNextTopic(Instant from) {
        Instant instantOfTheOldestTopic = topicList.get(0).getCreationInstant();
        return Duration.between(from, instantOfTheOldestTopic).getSeconds() / 60;
    }
}
