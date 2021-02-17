package ru.pankov.service.inter;

import org.springframework.data.domain.Page;
import ru.pankov.entity.Comment;

public interface CommentService {

    Page<Comment> getComments(Integer page);

    Comment addComment(String text);
}
