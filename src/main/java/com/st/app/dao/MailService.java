package com.st.app.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService  {

    @Autowired
    private JavaMailSender emailSender;

    Logger logger = LoggerFactory.getLogger(MailService.class);

    public void send(String to,String subject,String text){

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("improv-noreply@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);

    }

    public void send(String to, String subject, String email, File[] attachments){
        //TODO implement send with attachments
    }

}
