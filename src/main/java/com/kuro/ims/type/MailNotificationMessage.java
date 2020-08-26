package com.kuro.ims.type;

import com.kuro.ims.entity.Order;
import com.kuro.ims.entity.OrderProduct;
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
                message.setSubject(String.format("New Order %s", order.getInvoiceNumber()));
                return message;
            }
        },

    CUSTOMER_INVOICE
        {
            @Override
            public SimpleMailMessage getSimpleMailMessage(Object params)
            {
                Order order = (Order) params;
                StringBuilder template = new StringBuilder("Dear " + order.getCustomer().getName() + ",\n");
                template.append("Thank you for your order. Find below your purchase details:\n\n");
                template.append("Invoice Number: ").append(order.getInvoiceNumber()).append("\n");
                template.append("Date:").append(order.getCreatedAt()).append("\n\n\n");

                List<OrderProduct> orderProducts = order.getOrderProducts();
                for (int i = 0, orderProductsSize = orderProducts.size(); i < orderProductsSize; i++)
                {
                    OrderProduct p = orderProducts.get(i);
                    template
                        .append(i + 1)
                        .append(")     ")
                        .append(p.getProduct().getName())
                        .append("     ")
                        .append(p.getQuantity())
                        .append("     ")
                        .append(p.getUnitPrice())
                        .append("     ")
                        .append(p.getTotalPrice())
                        .append("\n\n\n");
                }

                template.append("Net Total: ").append(order.getNetAmount()).append("\n");
                template.append("VAT percentage: ").append(order.getVatPercentage()).append("\n");
                template.append("Gross Total: ").append(order.getGrossAmount()).append("\n");
                if (order.getCustomer() != null)
                {
                    template.append("Loyalty Points Used: ").append(order.getLoyaltyDiscountAmount() == null ? "0" : order.getLoyaltyDiscountAmount()).append("\n");
                    template.append("Loyalty Points Balance: ").append(order.getCustomer().getLoyaltyPoints() == null ? "0" : order.getCustomer().getLoyaltyPoints()).append("\n");
                }
                template.append("Total Paid: ").append(order.getTotalPaid()).append("\n");

                SimpleMailMessage message = new SimpleMailMessage();
                message.setText(template.toString());
                message.setSubject(String.format("Order purchase successful %s", order.getInvoiceNumber()));
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
