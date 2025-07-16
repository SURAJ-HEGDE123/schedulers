package com.example.scheduler.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendFailureAlert(String schedulerName, String errorMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("surajhegde150@gmail.com");
        message.setFrom("surajhegde150@gmail.com");
        message.setSubject("Scheduler Failed - " + schedulerName);
        message.setText("Scheduler '" + schedulerName + " 'failed after 3 retries.\nError: " + errorMessage);

        try{
            System.out.println("Sending email to surajhegde150@gmail.com");
        mailSender.send(message);
        System.out.println("Email sent successfully");
    } catch (Exception e){
            System.out.println(" Failed to send email: " +e.getMessage());
        }

        }
    }


