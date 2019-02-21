package br.com.caelum.forum.controller;

import br.com.caelum.forum.controller.dto.input.TopicSearchInputDto;
import br.com.caelum.forum.controller.dto.output.TopicBriefOutputDto;
import br.com.caelum.forum.controller.dto.output.TopicDashboardDto;
import br.com.caelum.forum.model.Category;
import br.com.caelum.forum.model.topic.domain.Topic;
import br.com.caelum.forum.repository.CategoryRepository;
import br.com.caelum.forum.repository.TopicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TopicController {

	private TopicRepository topicRepository;
	private CategoryRepository categoryRepository;

	public TopicController(TopicRepository topicRepository, CategoryRepository categoryRepository) {
		this.topicRepository = topicRepository;
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/api/topics")
	public Page<TopicBriefOutputDto> listAll(TopicSearchInputDto topicSearchInputDto,
											 @PageableDefault(sort="creationInstant",
													 direction = Sort.Direction.DESC)
									Pageable pageable) {
		Page<Topic> listaTopicos = topicRepository.findAll(topicSearchInputDto.buildSpecification(), pageable);
		return TopicBriefOutputDto.listFromTopics(listaTopicos);
	}

	@GetMapping("/api/topics/dashboard")
	public List<TopicDashboardDto> listDashboard() {
		List<Category> listaCategorias = categoryRepository.findAll();
		return listaCategorias.stream()
				.filter(category -> !category.getCategory().isPresent())
				.map(category ->
						TopicDashboardDto.montaDTO(category,
								topicRepository.getTotalTopicsByIdCategory(category.getId()),
								topicRepository.getTotalTopicsFromLastWeekByIdCategory(category.getId(), Instant.now().minus(7, ChronoUnit.DAYS)),
								topicRepository.getTotalUnansweredTopicsByIdCategory(category.getId()))
				)
				.collect(Collectors.toList());
	}
}
