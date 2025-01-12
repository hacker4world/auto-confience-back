package com.group.autoconfienceback.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    public void sendPasswordResetCode(String to, String name, String resetCode) throws MessagingException {

        Context context = new Context();

        context.setVariable("name", name);
        context.setVariable("code", resetCode);

        String templateContent = templateEngine.process("reset-password", context);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        helper.setTo(to);
        helper.setSubject("Reset your Auto-confience password");
        helper.setText(templateContent, true);

        javaMailSender.send(message);
    }

    public void sendAccountInformations(String email, String password) throws MessagingException {
        Context context = new Context();

        context.setVariable("email", email);
        context.setVariable("password", password);

        String templateContent = templateEngine.process("account-informations", context);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name()
        );

        helper.setTo(email);
        helper.setSubject("Your Auto-confience account informations");
        helper.setText(templateContent, true);

        javaMailSender.send(message);

    }

}
