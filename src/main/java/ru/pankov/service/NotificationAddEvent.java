package ru.pankov.service;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import ru.pankov.entity.Comment;

@Getter
public class NotificationAddEvent extends ApplicationEvent {

    private final Comment comment;

    public NotificationAddEvent(Object source, Comment comment) {
        super(source);
        this.comment = comment;
    }

}
