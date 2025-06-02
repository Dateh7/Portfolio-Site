package com.projectportfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String from, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("your_email@gmail.com"); // You receive this
        message.setSubject(subject);
        message.setText("From: " + from + "\n\nMessage:\n" + text);
        mailSender.send(message);
    }
}
