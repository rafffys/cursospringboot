package br.com.caelum.forum.controller;

import br.com.caelum.forum.controller.dto.input.NewAnswerInputDto;
import br.com.caelum.forum.model.User;
import br.com.caelum.forum.model.topic.domain.Topic;
import br.com.caelum.forum.repository.TopicRepository;
import br.com.caelum.forum.repository.UserRepository;
import br.com.caelum.forum.security.jwt.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.util.UriTemplate;

import javax.transaction.Transactional;
import java.net.URI;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
public class AnswerControllerTests {
    private static final String ENDPOINT = "/api/topics/{topicId}/answers";

    private Long topicId;
    private String jwt;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenManager tokenManager;

    @Before
    public void setup() throws RuntimeException {
        String rawPassword = "123456";
        User user = new User("Aluno da Caelum", "aluno@gmail.com", new BCryptPasswordEncoder().encode(rawPassword));
        User persistedUser = this.userRepository.save(user);
        Topic topic = new Topic("Descrição do Tópico", "Conteúdo do Tópico", persistedUser, null);
        Topic persistedTopic = this.topicRepository.save(topic);
        this.topicId = persistedTopic.getId();

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        user.getEmail(),
                        rawPassword));
        this.jwt = this.tokenManager.generateToken(authentication);
    }

    @Test
    public void shouldProcessSuccessFullyNewAnswerRequest() throws Exception {
        URI uri = new UriTemplate(ENDPOINT).expand(this.topicId);

        NewAnswerInputDto newAnswerInputDto = new NewAnswerInputDto();
        newAnswerInputDto.setContent("Não consigo subir servidor");

        MockHttpServletRequestBuilder request = post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + this.jwt)
                .content(new ObjectMapper().writeValueAsString(newAnswerInputDto));

        this.mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString(newAnswerInputDto.getContent())));
    }

    @Test
    public void shouldRejectNewAnswerRequest() throws Exception {
        URI uri = new UriTemplate(ENDPOINT).expand(this.topicId);

        NewAnswerInputDto inputDto = new NewAnswerInputDto();
        inputDto.setContent("bad");

        MockHttpServletRequestBuilder request = post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + this.jwt)
                .content(new ObjectMapper().writeValueAsString(inputDto));

        this.mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }




}
