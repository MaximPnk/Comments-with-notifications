package ru.pankov.service.inter;

import org.springframework.data.domain.Page;
import ru.pankov.entity.Notification;
import ru.pankov.service.NotificationAddEvent;

import java.util.concurrent.Future;

public interface NotificationService {

    Page<Notification> getNotifications(Integer page);

    Future<Notification> addNotification(NotificationAddEvent event);

}
