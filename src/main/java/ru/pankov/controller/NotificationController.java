package ru.pankov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pankov.entity.Notification;
import ru.pankov.err.ResourceNotFoundException;
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

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable Long id) {
        return notificationService.getNotification(id).orElseThrow(() -> new ResourceNotFoundException("Notification doesn't exist"));
    }
}
