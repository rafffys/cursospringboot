package br.com.caelum.forum.controller;

import java.util.List;
import java.util.stream.Collectors;

import br.com.caelum.forum.controller.dto.input.TopicSearchInputDto;
import br.com.caelum.forum.controller.dto.output.TopicBriefOutputDto;
import br.com.caelum.forum.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
	public Page<TopicBriefOutputDto> listAll(TopicSearchInputDto topicSearchInputDto,
											 @PageableDefault(sort="creationInstant",
													 direction = Sort.Direction.DESC)
									Pageable pageable) {
		Page<Topic> listaTopicos = topicRepository.findAll(topicSearchInputDto.buildSpecification(), pageable);
		return TopicBriefOutputDto.listFromTopics(listaTopicos);
	}
}
