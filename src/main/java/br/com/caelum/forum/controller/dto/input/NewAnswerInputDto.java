package br.com.caelum.forum.controller.dto.input;

import br.com.caelum.forum.exception.ResourceNotFoundException.ResourceNotFoundException;
import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.model.topic.domain.Topic;
import br.com.caelum.forum.repository.TopicRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class NewAnswerInputDto {
    @NotBlank
    @Size(min=10)
    private String content;

    public NewAnswerInputDto(String content) {
        this.content = content;
    }

    public NewAnswerInputDto() {
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Answer build(Long topicId, TopicRepository topicRepository, User owner) throws ResourceNotFoundException {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Tópico com id " + topicId + " não encontrado!"));
        return new Answer(this.getContent(), topic, owner);
    }

}
