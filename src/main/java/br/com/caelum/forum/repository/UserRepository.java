package br.com.caelum.forum.repository;

import br.com.caelum.forum.model.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
}
