package com.kuro.ims.event;

import com.kuro.ims.entity.Order;
import com.kuro.ims.entity.OrderProduct;
import com.kuro.ims.entity.Product;
import com.kuro.ims.entity.Setting;
import com.kuro.ims.service.MailNotificationService;
import com.kuro.ims.service.SettingService;
import com.kuro.ims.type.MailNotificationMessage;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@AllArgsConstructor
public class OrderEventListener
{
    private MailNotificationService mailNotificationService;

    private SettingService settingService;

    @Async
    @TransactionalEventListener
    public void orderCreatedEvent(Order order)
    {
        Setting storeSetting = settingService.getStoreSetting();
        mailNotificationService.sendSimpleMessage(storeSetting.getEmail(), MailNotificationMessage.ORDER_CREATED, order);

        List<String> lowStockProducts =
            order.getOrderProducts().stream()
                .map(OrderProduct::getProduct)
                .filter(p -> p.getAvailableQuantity() < p.getMinQuantity())
                .map(Product::getName)
                .collect(Collectors.toList());

        if (lowStockProducts.size() > 0)
        {
            mailNotificationService.sendSimpleMessage(storeSetting.getEmail(), MailNotificationMessage.LOW_STOCK, lowStockProducts);
        }
    }
}
