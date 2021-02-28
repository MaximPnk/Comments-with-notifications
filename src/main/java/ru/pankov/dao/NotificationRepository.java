package ru.pankov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.entity.Comment;
import ru.pankov.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Long countAllByDelivered(Boolean delivered);

    Notification findByComment(Comment comment);
}
