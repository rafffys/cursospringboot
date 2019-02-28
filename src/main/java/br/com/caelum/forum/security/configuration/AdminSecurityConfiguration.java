package br.com.caelum.forum.security.configuration;

import br.com.caelum.forum.security.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
@Order(1)
public class AdminSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UsersService usersService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/admin/**").authorizeRequests()
                .anyRequest().hasRole("ADMIN")
            .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.usersService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
