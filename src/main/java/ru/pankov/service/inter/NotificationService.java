package ru.pankov.service.inter;

import org.springframework.data.domain.Page;
import ru.pankov.entity.Comment;
import ru.pankov.entity.Notification;

import java.util.Optional;

public interface NotificationService {

    Page<Notification> getNotifications(Integer page);

    Optional<Notification> getNotification(Long id);

    void addNotification(Comment comment, Long exec);
}
