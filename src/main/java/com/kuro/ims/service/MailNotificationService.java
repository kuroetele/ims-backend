package com.kuro.ims.service;

import com.kuro.ims.type.MailNotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailNotificationService
{
    private JavaMailSender javaMailSender;


    public void sendSimpleMessage(String to, MailNotificationMessage mailNotificationMessage, Object params)
    {
        SimpleMailMessage message = mailNotificationMessage.getSimpleMailMessage(params);
        message.setFrom("noreply@ims.com");
        message.setTo(to);
        javaMailSender.send(message);
    }
}
