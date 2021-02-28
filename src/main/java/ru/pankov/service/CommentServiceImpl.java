package ru.pankov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pankov.dao.CommentRepository;
import ru.pankov.entity.Comment;
import ru.pankov.err.CommentException;
import ru.pankov.logic.BusinessLogic;
import ru.pankov.service.inter.CommentService;
import ru.pankov.service.inter.NotificationService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NotificationService notificationService;

    @Override
    public Page<Comment> getComments(Integer page) {
        return commentRepository.findAll(PageRequest.of(page - 1, 10));
    }

    @Override
    @Transactional(rollbackFor = CommentException.class)
    @Async
    public Future<Comment> addComment(String text) {
        Comment comment = commentRepository.save(new Comment(text));

        try {
            BusinessLogic.doSomeWorkOnCommentCreation();
        } catch (RuntimeException e) {
            log.info(String.format("Comment #%d: failure", comment.getId()));
            throw new CommentException("Problems with comments");
        }
        log.info(String.format("Comment #%d: success", comment.getId()));

        notificationService.addNotification(comment);
        return CompletableFuture.completedFuture(comment);
    }
}
