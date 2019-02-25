package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.Course;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CourseRepository extends Repository<Course, Long> {
    Optional<Course> findByName(String name);
}
