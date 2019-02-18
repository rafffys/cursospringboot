package br.com.caelum.forum.model.topic.domain;

import br.com.caelum.forum.model.Answer;

public interface TopicState {

    void registerNewReply(Topic topic, Answer newReply);
	void markAsSolved(Topic topic);
	void close(Topic topic);
}
