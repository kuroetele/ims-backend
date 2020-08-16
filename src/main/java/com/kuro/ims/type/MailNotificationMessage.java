package com.kuro.ims.type;

import com.kuro.ims.entity.Order;
import com.kuro.ims.entity.User;
import java.util.List;
import org.springframework.mail.SimpleMailMessage;

public enum MailNotificationMessage
{
    USER_CREATED
        {
            @Override
            public SimpleMailMessage getSimpleMailMessage(Object params)
            {
                User user = (User) params;
                String template = "A new account has been created for you:\n\n" +
                    "username: %s\n" +
                    "password: %s\n" +
                    "role: %s\n";
                SimpleMailMessage message = new SimpleMailMessage();
                message.setText(String.format(
                    template,
                    user.getEmail(),
                    user.getPlainPassword(),
                    user.getRole().name()));
                message.setSubject("New user profile");
                return message;
            }
        },

    ORDER_CREATED
        {
            @Override
            public SimpleMailMessage getSimpleMailMessage(Object params)
            {
                Order order = (Order) params;
                String template = "New Order has been created:\n\n" +
                    "Invoice Number: %s\n" +
                    "Amount: %s\n" +
                    "Date: %s\n";
                SimpleMailMessage message = new SimpleMailMessage();
                message.setText(String.format(
                    template,
                    order.getInvoiceNumber(),
                    order.getGrossAmount(),
                    order.getCreatedAt()));
                message.setSubject(String.format("New Order", order.getInvoiceNumber()));
                return message;
            }
        },

    LOW_STOCK
        {
            @Override
            public SimpleMailMessage getSimpleMailMessage(Object params)
            {
                List<String> products = (List<String>) params;
                StringBuilder template = new StringBuilder("The following products (%d) are running low:\n\n");
                for (String p : products)
                {
                    template.append(p).append("\n");
                }
                SimpleMailMessage message = new SimpleMailMessage();
                message.setText(String.format(template.toString(), products.size()));
                message.setSubject("Low stock warning");
                return message;
            }
        };


    public abstract SimpleMailMessage getSimpleMailMessage(Object params);
}
