package ru.pankov.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.pankov.dao.CommentRepository;
import ru.pankov.dao.NotificationRepository;
import ru.pankov.entity.Comment;
import ru.pankov.err.CommentException;
import ru.pankov.service.inter.CommentService;
import ru.pankov.service.inter.NotificationService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Slf4j
class CommentControllerTest {

    int count = 1000;

    @Autowired
    CommentService commentService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Test
    void addComment() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            adding(i);
        }
        log.info("Tests execute in " + (System.currentTimeMillis() - start) + " ms");
        log.info(String.format("Success chance of adding a comment = %f", (double) commentRepository.count() / 1000));
        log.info(String.format("Success chance of delivering a notification = %f", (double) notificationRepository.countAllByDelivered(true) / notificationRepository.count()));
    }

    void adding(int i) {

        Thread  thread = new Thread(() -> {
            boolean success = false;
            try {
                commentService.addComment("" + i).get();
                success = true;
            } catch (Exception e) {
                if (e.getCause() instanceof CommentException) {
                    assertNull(commentRepository.findByText("" + i));
                } else {
                    e.printStackTrace();
                }
            }

            if (success) {
                Comment comment = commentRepository.findByText("" + i);
                assertNotNull(comment);
                assertNotNull(notificationRepository.findByComment(comment));
            }
        });

        thread.start();
        if (i == count - 1) {
            try {
                thread.join();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}