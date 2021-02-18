package ru.pankov.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import ru.pankov.logic.BusinessLogic;
import ru.pankov.dao.CommentRepository;
import ru.pankov.entity.Comment;
import ru.pankov.err.RandomException;
import ru.pankov.service.inter.CommentService;
import ru.pankov.service.inter.NotificationService;

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
    @Transactional
    public Comment addComment(String text) {
        long start = System.currentTimeMillis();
        Comment comment = commentRepository.save(new Comment(text));

        try {
            // При времени > 1 секунды в методе doSomeWorkOnCommentCreation()
            // необходимо добавить логику вывода из спяшего состояния из Notification
            // В данных условиях такой необходимости нет
            BusinessLogic.doSomeWorkOnCommentCreation();
        } catch (RuntimeException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.info(String.format("Comment #%d: failure", comment.getId()));
            throw new RandomException("Problems on the server");
        }
        log.info(String.format("Comment #%d: success", comment.getId()));

        notificationService.addNotification(comment, System.currentTimeMillis() - start);
        return comment;
    }
}
