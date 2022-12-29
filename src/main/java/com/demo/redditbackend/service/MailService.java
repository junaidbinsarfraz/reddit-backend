package com.demo.redditbackend.service;

import com.demo.redditbackend.exception.SpringRedditException;
import com.demo.redditbackend.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {

    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;

    @Async
    public void sendEmail(NotificationEmail notificationEmail) throws SpringRedditException {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("test@email.com");
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(this.mailContentBuilder.build(notificationEmail.getBody()));
        };
        try {
            this.mailSender.send(messagePreparator);
            log.info("Activation Email Sent.");
        } catch (Exception e) {
            throw new SpringRedditException("Exception occurred while sending activation email to " + notificationEmail.getRecipient());
        }
    }
}
