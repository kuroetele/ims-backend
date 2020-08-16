package com.kuro.ims.event;

import com.kuro.ims.entity.User;
import com.kuro.ims.service.MailNotificationService;
import com.kuro.ims.type.MailNotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserEventListener
{
    private MailNotificationService mailNotificationService;


    @EventListener
    public void userCreatedEvent(User user)
    {
        mailNotificationService.sendSimpleMessage(user.getEmail(), MailNotificationMessage.USER_CREATED, user);
    }
}
