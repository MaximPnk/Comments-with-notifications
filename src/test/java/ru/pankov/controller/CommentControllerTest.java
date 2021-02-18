package ru.pankov.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pankov.dao.CommentRepository;
import ru.pankov.dao.NotificationRepository;
import ru.pankov.err.RandomException;
import ru.pankov.err.ResourceNotFoundException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class CommentControllerTest {

    @Autowired
    CommentController commentController;

    @Autowired
    NotificationController notificationController;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Test
    void addComment() {
        Thread curr = Thread.currentThread();
        new Thread(() -> {
            while (true) {
                if (curr.getState().equals(Thread.State.TIMED_WAITING)) {
                    curr.interrupt();
                }
            }
        }).start();

        long commentCount = 0;
        long notificationCount = 0;

        for (int i = 0; i < 1000; i++) {
            boolean success = false;
            commentCount++;

            try {
                commentController.addComment("asd");
                success = true;
            } catch (RandomException e) {
                long finalCount = commentCount;
                assertThrows(ResourceNotFoundException.class, () -> commentController.getComment(finalCount));
            }

            if (success) {
                notificationCount++;
                assertNotNull(commentController.getComment(commentCount));
                assertNotNull(notificationController.getNotification(notificationCount));
            }
        }

        log.info(String.format("Success chance of adding a comment = %f", (double) commentRepository.count() / 1000));
        log.info(String.format("Success chance of delivering a notification = %f", (double) notificationRepository.countAllByDelivered(true) / notificationRepository.count()));
    }
}