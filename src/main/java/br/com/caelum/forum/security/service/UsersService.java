package br.com.caelum.forum.security.service;

import br.com.caelum.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }

    public UserDetails loadUserById(Long userId) {
        return this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado!"));
    }
}
