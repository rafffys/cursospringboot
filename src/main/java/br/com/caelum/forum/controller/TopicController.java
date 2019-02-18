package br.com.caelum.forum.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.forum.model.Category;
import br.com.caelum.forum.model.Course;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.model.topic.domain.Topic;

@RestController
public class TopicController {
	@GetMapping("/api/topics")
	public List<Topic> listAll() {
		Category categoria = new Category("Java", new Category("Programação"));
		Course curso = new Course("C#",categoria);
		Topic topico = new Topic("Tópico de Teste", "Conteúdo de Teste", new User("Fulano", "fulano@gmail.com", "123456"), curso );
		return Collections.singletonList(topico);
	}
}
