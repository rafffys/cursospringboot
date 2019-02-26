package br.com.caelum.forum.service.infra;

import br.com.caelum.forum.infra.NewReplyMailFactory;
import br.com.caelum.forum.model.Answer;
import br.com.caelum.forum.model.topic.domain.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ForumMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NewReplyMailFactory newReplyMailFactory;

    @Async
    public void sendNewReplyMailAsync(Answer answer) {
        Topic answeredTopic = answer.getTopic();

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

            messageHelper.setTo(answeredTopic.getOwnerEmail());
            messageHelper.setSubject("Novo comentário em: " + answeredTopic.getShortDescription());
            String messageContent = this.newReplyMailFactory.generateNewReplyMailContent(answer);
            messageHelper.setText(messageContent, true);

        };

        try {
            mailSender.send(messagePreparator);
        } catch (MailException e) {
            throw new MailServiceException("Não foi possível enviar o email. ", e);
        }
    }
}
