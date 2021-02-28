package ru.pankov.service.inter;

import org.springframework.data.domain.Page;
import ru.pankov.entity.Comment;

import java.util.concurrent.Future;

public interface CommentService {

    Page<Comment> getComments(Integer page);

    Future<Comment> addComment(String text);
}
