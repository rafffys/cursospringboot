package br.com.caelum.forum.controller.dto.input;

import br.com.caelum.forum.exception.ResourceNotFoundException.ResourceNotFoundException;
import br.com.caelum.forum.model.Course;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.model.topic.domain.Topic;
import br.com.caelum.forum.repository.CourseRepository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class NewTopicInputDto {
    @NotBlank
    @Size(min = 10)
    private String shortDescription;
    @NotBlank
    @Size(min=10)
    private String content;
    @NotEmpty
    private String courseName;

    public NewTopicInputDto(String shortDescription, String content, String courseName) {
        this.shortDescription = shortDescription;
        this.content = content;
        this.courseName = courseName;
    }

    public NewTopicInputDto() {
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
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Topic build(User owner, CourseRepository courseRepository) throws ResourceNotFoundException {
        Course course = courseRepository.findByName(this.courseName).orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado!"));
        return new Topic(this.shortDescription, this.content, owner, course);
    }
}
