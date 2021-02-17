package ru.pankov.service.inter;

import org.springframework.data.domain.Page;
import ru.pankov.entity.Comment;
import ru.pankov.entity.Notification;

public interface NotificationService {

    Page<Notification> getNotifications(Integer page);

    Notification addNotification(Comment comment);
}
