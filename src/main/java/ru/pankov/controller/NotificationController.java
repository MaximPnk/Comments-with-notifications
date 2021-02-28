package ru.pankov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.pankov.entity.Notification;
import ru.pankov.service.inter.NotificationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/")
    public List<Notification> getNotifications(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return notificationService.getNotifications(page).getContent();
    }
}
