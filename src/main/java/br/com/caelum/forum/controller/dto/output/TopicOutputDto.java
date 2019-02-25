package br.com.caelum.forum.controller.dto.output;

import br.com.caelum.forum.model.topic.domain.Topic;
import br.com.caelum.forum.model.topic.domain.TopicStatus;

import java.time.Instant;

public class TopicOutputDto {
    private Long id;
    private String shortDescription;
    private String content;
    private TopicStatus status;
    private int numberOfResponses;
    private Instant lastUpdate;
    private Instant creationInstant;

    private String ownerName;
    private String courseName;
    private String subcategoryName;
    private String categoryName;

    public TopicOutputDto(Topic topic) {
        this.id = topic.getId();
        this.shortDescription = topic.getShortDescription();
        this.content = topic.getContent();
        this.status = topic.getStatus();
        this.numberOfResponses = topic.getNumberOfAnswers();
        this.lastUpdate = topic.getLastUpdate();
        this.creationInstant = topic.getCreationInstant();
        this.ownerName = topic.getOwner().getName();
        this.courseName = topic.getCourse().getName();
        this.subcategoryName = topic.getCourse().getSubcategoryName();
        this.categoryName = topic.getCourse().getCategoryName();
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

    public int getNumberOfResponses() {
        return numberOfResponses;
    }

    public void setNumberOfResponses(int numberOfResponses) {
        this.numberOfResponses = numberOfResponses;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Instant getCreationInstant() {
        return creationInstant;
    }

    public void setCreationInstant(Instant creationInstant) {
        this.creationInstant = creationInstant;
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
}
