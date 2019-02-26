package br.com.caelum.forum.controller.dto.output.AnswerBriefOutputDto;

import br.com.caelum.forum.model.Answer;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class AnswerBriefOutputDto {
    private Long id;
    private String content;
    private Instant creationTime;
    private boolean solution;
    private String ownerName;

    public AnswerBriefOutputDto(Answer answer) {
        this.setId(answer.getId());
        this.setContent(answer.getContent());
        this.setCreationTime(answer.getCreationTime());
        this.setSolution(answer.isSolution());
        this.setOwnerName(answer.getOwner().getName());
    }

    public static List<AnswerBriefOutputDto> listFromAnswers(List<Answer> answersList) {
        return answersList.stream().map(AnswerBriefOutputDto::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Instant creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isSolution() {
        return solution;
    }

    public void setSolution(boolean solution) {
        this.solution = solution;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}
