package br.com.caelum.forum.service;

import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.repository.AnswerRepository;
import br.com.caelum.forum.service.infra.ForumMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewReplyProcessorService {
    private static final Logger logger = LoggerFactory.getLogger(NewReplyProcessorService.class);

    private AnswerRepository answerRepository;
    private ForumMailService forumMailService;

    @Autowired
    public NewReplyProcessorService(AnswerRepository answerRepository, ForumMailService forumMailService) {
        this.answerRepository = answerRepository;
        this.forumMailService = forumMailService;
    }

    public void execute(Answer answer) {
        this.answerRepository.save(answer);
        this.forumMailService.sendNewReplyMail(answer);
    }
}
