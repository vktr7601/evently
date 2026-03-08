package com.evently.notification.notificationContent.contract;

import com.evently.notification.notificationContent.model.NotificationContent;
import events.promoCode.PromoCodeCreated;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class PromoCodeCreatedContentProvider implements NotificationContentProvider<PromoCodeCreated> {
    private final TemplateEngine templateEngine;

    @Override
    public NotificationContent generateNotificationContent(PromoCodeCreated event) {
        Context context = new Context();

        context.setVariable("promoCode", event.getPromoCode());
        context.setVariable("discountPercentage",
                event.getDiscountPercentage());
        context.setVariable("discountType", event.getDiscountType());

        if (event.getExpirationDate() != null) {
            ZonedDateTime expiryZoned =
                    event.getExpirationDate().atZone(ZoneId.systemDefault());
            context.setVariable("expirationDate", expiryZoned);
        }

        context.setVariable("browseEventsUrl", "https://evently.com/events");

        String html = templateEngine.process("promo-code", context);
        String title = "A little something for you...";

        NotificationContent notificationContent = new NotificationContent();
        notificationContent.setTitle(title);
        notificationContent.setHtmlBody(html);
        return notificationContent;
    }
}