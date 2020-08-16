package com.kuro.ims.service;

import com.kuro.ims.exception.ImsException;
import com.kuro.ims.type.MailNotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.MailException;
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
        try
        {
            javaMailSender.send(message);
        }
        catch (MailException e)
        {
           throw new ImsException("Unable to send mail. Kindly check mail configurations");
        }
    }
}
