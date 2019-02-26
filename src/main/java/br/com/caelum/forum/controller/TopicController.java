package br.com.caelum.forum.controller;

import br.com.caelum.forum.controller.dto.input.NewAnswerInputDto;
import br.com.caelum.forum.controller.dto.input.NewTopicInputDto;
import br.com.caelum.forum.controller.dto.input.TopicSearchInputDto;
import br.com.caelum.forum.controller.dto.output.*;
import br.com.caelum.forum.exception.ResourceNotFoundException.ResourceNotFoundException;
import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.Category;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.model.topic.domain.Topic;
import br.com.caelum.forum.repository.AnswerRepository;
import br.com.caelum.forum.repository.CategoryRepository;
import br.com.caelum.forum.repository.CourseRepository;
import br.com.caelum.forum.repository.TopicRepository;
import br.com.caelum.forum.validator.NewTopicCustomValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

	private TopicRepository topicRepository;
	private CategoryRepository categoryRepository;
	private CourseRepository courseRepository;
	private AnswerRepository answerRepository;

	public TopicController(TopicRepository topicRepository,
						   CategoryRepository categoryRepository,
						   CourseRepository courseRepository,
						   AnswerRepository answerRepository) {
		this.topicRepository = topicRepository;
		this.categoryRepository = categoryRepository;
		this.courseRepository = courseRepository;
		this.answerRepository = answerRepository;
	}

	@InitBinder("newTopicInputDto")
	public void initBinder(WebDataBinder binder, @AuthenticationPrincipal User loggedUser) {
		binder.addValidators(new NewTopicCustomValidator(topicRepository, loggedUser));
	}

	@GetMapping
	public Page<TopicBriefOutputDto> listAll(TopicSearchInputDto topicSearchInputDto,
											 @PageableDefault(sort="creationInstant",
													 direction = Sort.Direction.DESC)
									Pageable pageable) {
		Page<Topic> listaTopicos = topicRepository.findAll(topicSearchInputDto.buildSpecification(), pageable);
		return TopicBriefOutputDto.listFromTopics(listaTopicos);
	}

	@GetMapping("/{id}")
	public TopicCompleteOutputDto findById(@PathVariable("id") Long topicId) throws ResourceNotFoundException {
		Topic topic = topicRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("Tópico com id " + topicId + " não encontrado!") );
		return new TopicCompleteOutputDto(topic);
	}

	@GetMapping("/dashboard")
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

	@PostMapping
	public ResponseEntity<TopicOutputDto> createTopic(@Valid @RequestBody NewTopicInputDto newTopicInputDto, @AuthenticationPrincipal User loggedUser, UriComponentsBuilder uriComponentsBuilder) throws ResourceNotFoundException {
		Topic topic = newTopicInputDto.build(loggedUser, courseRepository);
		this.topicRepository.save(topic);
		return ResponseEntity
				.created(uriComponentsBuilder.path("/api/topics/{id}").buildAndExpand(topic.getId()).toUri())
				.body(new TopicOutputDto(topic));
	}

	@PostMapping("/{id}/answers")
	public ResponseEntity<AnswerBriefOutputDto> createAnswer(@PathVariable("id") Long topicId, @Valid @RequestBody NewAnswerInputDto newAnswerInputDto, @AuthenticationPrincipal User loggedUser, UriComponentsBuilder uriComponentsBuilder) throws ResourceNotFoundException {
		Answer answer = newAnswerInputDto.build(topicId, topicRepository, loggedUser);
		this.answerRepository.save(answer);
		return ResponseEntity
				.created(uriComponentsBuilder.path("/api/topics/{id}").buildAndExpand(answer.getTopic().getId()).toUri())
				.body(new AnswerBriefOutputDto(answer));
	}
}
