package br.com.caelum.forum.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.forum.controller.dto.input.TopicSearchInputDto;
import br.com.caelum.forum.controller.dto.output.TopicBriefOutputDto;
import br.com.caelum.forum.repository.TopicRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.forum.model.topic.domain.Topic;

@RestController
public class TopicController {

	private TopicRepository topicRepository;

	public TopicController(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	@GetMapping("/api/topics")
	public List<TopicBriefOutputDto> listAll(TopicSearchInputDto topicSearchInputDto) {
		List<Topic> listaTopicos = topicRepository.findAll(topicSearchInputDto.buildSpecification());
		return listaTopicos.stream().map(TopicBriefOutputDto::new).collect(Collectors.toList());
	}
}
