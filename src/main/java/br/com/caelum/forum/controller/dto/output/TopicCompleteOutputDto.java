package br.com.caelum.forum.controller.dto.output;

import br.com.caelum.forum.model.topic.domain.Topic;
import br.com.caelum.forum.model.topic.domain.TopicStatus;
import org.springframework.data.domain.Page;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class TopicCompleteOutputDto {
	private Long id;
	private String shortDescription;
	private String content;
	private TopicStatus status;
	private Instant creationInstant;
	private Instant lastUpdate;

	private long secondsSinceLastUpdate;
	private String ownerName;
	private String courseName;
	private String subcategoryName;
	private String categoryName;
	private int numberOfResponses;
	private List<AnswerBriefOutputDto> answers;

	public TopicCompleteOutputDto(Topic topic) {
		this.setId(topic.getId());
		this.setShortDescription(topic.getShortDescription());
		this.setSecondsSinceLastUpdate(getSecondsSince(topic.getLastUpdate()));
		this.setOwnerName(topic.getOwner().getName());
		this.setCourseName(topic.getCourse().getName());
		this.setSubcategoryName(topic.getCourse().getSubcategory().getName());
		this.setCategoryName(topic.getCourse().getCategoryName());
		this.setNumberOfResponses(topic.getNumberOfAnswers());
		this.setLastUpdate(topic.getLastUpdate());
		this.setCreationInstant(topic.getCreationInstant());
		this.setContent(topic.getContent());
		this.setStatus(topic.getStatus());
		this.setAnswers(AnswerBriefOutputDto.listFromAnswers(topic.getAnswers()));
	}

	public List<AnswerBriefOutputDto> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerBriefOutputDto> answers) {
		this.answers = answers;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public TopicStatus getStatus() {
		return status;
	}

	public void setStatus(TopicStatus status) {
		this.status = status;
	}

	public Instant getCreationInstant() {
		return creationInstant;
	}

	public void setCreationInstant(Instant creationInstant) {
		this.creationInstant = creationInstant;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	private long getSecondsSince(Instant lastUpdate) {
		return Duration.between(lastUpdate, Instant.now()).get(ChronoUnit.SECONDS);
	}

	public static List<TopicCompleteOutputDto> listFromTopics(List<Topic> listaTopicos) {
		return listaTopicos.stream().map(TopicCompleteOutputDto::new).collect(Collectors.toList());
	}

	public static Page<TopicCompleteOutputDto> listFromTopics(Page<Topic> pageTopicos) {
		return pageTopicos.map(TopicCompleteOutputDto::new);
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public long getSecondsSinceLastUpdate() {
		return secondsSinceLastUpdate;
	}
	public void setSecondsSinceLastUpdate(long secondsSinceLastUpdate) {
		this.secondsSinceLastUpdate = secondsSinceLastUpdate;
	}

	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSubcategoryName() {
		return subcategoryName;
	}
	public void setSubcategoryName(String subcategoryName) {
		this.subcategoryName = subcategoryName;
	}

	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getNumberOfResponses() {
		return numberOfResponses;
	}
	public void setNumberOfResponses(int numberOfResponses) {
		this.numberOfResponses = numberOfResponses;
	}
}
