package ru.pankov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pankov.dao.NotificationRepository;
import ru.pankov.entity.Notification;
import ru.pankov.logic.BusinessLogic;
import ru.pankov.service.inter.NotificationService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Page<Notification> getNotifications(Integer page) {
        return notificationRepository.findAll(PageRequest.of(page - 1, 10));
    }

    @Override
    @Async
    @EventListener
    @Transactional
    public Future<Notification> addNotification(NotificationAddEvent event) {
        Notification notification = notificationRepository.save(new Notification(event.getComment()));

        try {
            BusinessLogic.doSomeWorkOnNotification();
        } catch (RuntimeException e) {
            notification.setDelivered(false);
        }

        log.info(String.format("Notification #%d: " + (notification.getDelivered() ? "success" : "failure"), notification.getId()));
        return CompletableFuture.completedFuture(notification);
    }

}
