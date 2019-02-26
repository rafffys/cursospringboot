package br.com.caelum.forum.service.infra;

import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.topic.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ForumMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendNewReplyMail(Answer answer) {
        Topic answeredTopic = answer.getTopic();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(answeredTopic.getOwnerEmail());
        message.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());
        message.setText("Olá, " + answeredTopic.getOwnerEmail() + "!\n\n" +
                "Há uma nova mensagem no forum: " + answer.getOwnerName() + " comentou no tópico: " +
                answeredTopic.getShortDescription());
        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new MailServiceException("Não foi possível enviar o email. ", e);
        }
    }
}
