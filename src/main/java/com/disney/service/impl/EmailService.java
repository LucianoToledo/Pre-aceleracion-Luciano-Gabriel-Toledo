package com.disney.service.impl;

import com.disney.dto.response.UserEmailResponse;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public String sendMail(UserEmailResponse user) throws MessagingException {
        Context context = new Context();
        context.setVariable("user", user);

        String process = templateEngine.process("emails/welcome", context);
        javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome " + user.getName());
        helper.setText(process, true);
        helper.setTo(user.getName()); //name = email
        javaMailSender.send(mimeMessage);
        return "Sent";
    }
}

