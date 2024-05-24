package com.tranvansi.ecommerce.common.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tranvansi.ecommerce.common.structures.MailStructure;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(MailStructure structure) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(from);
        simpleMailMessage.setTo(structure.getTo());
        simpleMailMessage.setSubject(structure.getSubject());
        simpleMailMessage.setText(structure.getContent());

        mailSender.send(simpleMailMessage);
    }
}
