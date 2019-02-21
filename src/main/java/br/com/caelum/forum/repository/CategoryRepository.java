package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.Category;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface CategoryRepository extends Repository<Category, Long> {
    List<Category> findAll();
}