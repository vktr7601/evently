package com.evently.notification.notifications.contract;

import com.evently.notification.notificationContent.model.NotificationContent;
import events.user.UserRegisteredEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class UserRegisteredContentProvider implements NotificationContentProvider<UserRegisteredEvent> {
    private final TemplateEngine templateEngine;

    @Override
    public NotificationContent generateNotificationContent(UserRegisteredEvent event) {
        Context context = new Context();
        context.setVariable("userName",
                event.getFirstName() + " " + event.getLastName());
        context.setVariable("userEmail", event.getEmail());

        context.setVariable("exploreUrl", "http://localhost:9000/events");

        String html = templateEngine.process("welcome-email", context);
        String title = "Welcome to the Evently system";

        NotificationContent notificationContent = new NotificationContent();
        notificationContent.setTitle(title);
        notificationContent.setHtmlBody(html);
        return notificationContent;
    }
}