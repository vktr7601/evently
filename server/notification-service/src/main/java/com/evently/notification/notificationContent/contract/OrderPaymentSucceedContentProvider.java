package com.evently.notification.notificationContent.contract;

import com.evently.notification.notificationContent.model.NotificationContent;
import events.order.OrderPaymentSucceededEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class OrderPaymentSucceedContentProvider implements NotificationContentProvider<OrderPaymentSucceededEvent> {
    private final TemplateEngine templateEngine;

    @Override
    public NotificationContent generateNotificationContent(OrderPaymentSucceededEvent event) {
        Context context = new Context();

        context.setVariable("totalAmount", event.getTotalAmount().setScale(2,
                RoundingMode.HALF_UP).toString());
        context.setVariable("orderHistoryUrl", "http://localhost:3000/orders" +
                "/details/" + event.getOrderNumber());

        context.setVariable("orderId", event.getOrderNumber());

        var html = templateEngine.process("order-success", context);
        NotificationContent notificationContent = new NotificationContent();
        notificationContent.setTitle("Order Finished");
        notificationContent.setHtmlBody(html);
        return notificationContent;
    }
}