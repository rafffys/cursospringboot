package br.com.caelum.forum.validator;

import br.com.caelum.forum.controller.dto.input.NewTopicInputDto;
import br.com.caelum.forum.model.PossibleSpam;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.model.topic.domain.Topic;
import br.com.caelum.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NewTopicCustomValidator implements Validator {

    private TopicRepository topicRepository;
    private User loggedUser;

    public NewTopicCustomValidator(TopicRepository topicRepository, User loggedUser) {
        this.topicRepository = topicRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return NewTopicInputDto.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Instant oneHourAgo = Instant.now().minus(1, ChronoUnit.HOURS);
        List<Topic> topicList = topicRepository.findByOwnerAndCreationInstantAfterOrderByCreationInstantAsc(loggedUser, oneHourAgo);
        PossibleSpam possibleSpam = new PossibleSpam(topicList);
        if (possibleSpam.hasTopicLimitExceeded()) {
            long minutesToNextTopic = possibleSpam.minutesToNextTopic(oneHourAgo);
            errors.reject("newTopicInputDto.limit.exceeded",
                    new Object[] {minutesToNextTopic},
                    "O limite individual de novos tópicos por hora foi excedido!");
        }
    }
}
