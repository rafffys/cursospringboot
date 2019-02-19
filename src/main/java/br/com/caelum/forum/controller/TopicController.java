package br.com.caelum.forum.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.forum.controller.dto.output.TopicBriefOutputDto;
import br.com.caelum.forum.repository.TopicRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.forum.model.Category;
import br.com.caelum.forum.model.Course;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.model.topic.domain.Topic;

@RestController
public class TopicController {

	private TopicRepository topicRepository;

	public TopicController(TopicRepository topicRepository) {
		this.topicRepository = topicRepository;
	}

	@GetMapping("/api/topics")
	public List<TopicBriefOutputDto> listAll() {
		List<Topic> listaTopicos = topicRepository.listAll();
		return listaTopicos.stream().map(TopicBriefOutputDto::new).collect(Collectors.toList());
	}
}
