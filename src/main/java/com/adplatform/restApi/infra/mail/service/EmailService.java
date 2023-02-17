package com.adplatform.restApi.infra.mail.service;

import com.adplatform.restApi.global.value.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @author Seohyun Lee
 * @since 1.0
 */
@RequiredArgsConstructor
@Component
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(Email email, String subject, String message, boolean html) {
        MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(email.getAddress());
            messageHelper.setSubject(subject);
            messageHelper.setText(message, html);

            this.mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
