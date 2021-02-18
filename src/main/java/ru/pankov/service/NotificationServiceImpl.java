package ru.pankov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pankov.err.AppError;
import ru.pankov.err.ResourceNotFoundException;
import ru.pankov.logic.BusinessLogic;
import ru.pankov.dao.NotificationRepository;
import ru.pankov.entity.Comment;
import ru.pankov.entity.Notification;
import ru.pankov.service.inter.NotificationService;

import java.util.Optional;

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
    public Optional<Notification> getNotification(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    @Transactional
    public void addNotification(Comment comment, Long exec) {
        Notification notification = notificationRepository.save(new Notification(comment));

        try {
            Thread curr = Thread.currentThread();
            new Thread(() -> {
                try {
                    Thread.sleep(1000-exec);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (curr.getState().equals(Thread.State.TIMED_WAITING)) {
                    log.info("INTERRUPTED");
                    curr.interrupt();
                }
            }).start();
            BusinessLogic.doSomeWorkOnNotification();
        } catch (RuntimeException e) {
            notification.setDelivered(false);
        }

        if (notification.getDelivered() == null) {
            notification.setDelivered(true);
        }

        log.info(String.format("Notification #%d: " + (notification.getDelivered() ? "success" : "failure"), notification.getId()));
    }
}
