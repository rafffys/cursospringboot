package br.com.caelum.forum.controller;

import br.com.caelum.forum.controller.dto.input.NewAnswerInputDto;
import br.com.caelum.forum.controller.dto.output.AnswerBriefOutputDto;
import br.com.caelum.forum.exception.ResourceNotFoundException.ResourceNotFoundException;
import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.repository.TopicRepository;
import br.com.caelum.forum.service.NewReplyProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
public class AnswerController {

    private TopicRepository topicRepository;
    private NewReplyProcessorService newReplyProcessorService;

    @Autowired
    public AnswerController(TopicRepository topicRepository, NewReplyProcessorService newReplyProcessorService) {
        this.topicRepository = topicRepository;
        this.newReplyProcessorService = newReplyProcessorService;
    }

    @PostMapping("/api/topics/{id}/answers")
    public ResponseEntity<AnswerBriefOutputDto> createAnswer(@PathVariable("id") Long topicId, @Valid @RequestBody NewAnswerInputDto newAnswerInputDto, @AuthenticationPrincipal User loggedUser, UriComponentsBuilder uriComponentsBuilder) throws ResourceNotFoundException {
        Answer answer = newAnswerInputDto.build(topicId, topicRepository, loggedUser);
        this.newReplyProcessorService.execute(answer);
        return ResponseEntity
                .created(uriComponentsBuilder.path("/api/topics/{id}/answers/{answer}").buildAndExpand(topicId,answer.getId()).toUri())
                .body(new AnswerBriefOutputDto(answer));
    }
}
