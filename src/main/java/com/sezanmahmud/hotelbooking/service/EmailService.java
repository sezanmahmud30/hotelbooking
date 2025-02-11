package com.sezanmahmud.hotelbooking.service;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    public void sendSimpleMail(String to, String subject, String body) throws MessagingException {

        MimeMessage massage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(massage, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        javaMailSender.send(massage);
    }


}
