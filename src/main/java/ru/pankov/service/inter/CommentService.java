package ru.pankov.service.inter;

import org.springframework.data.domain.Page;
import ru.pankov.entity.Comment;

import java.util.Optional;

public interface CommentService {

    Page<Comment> getComments(Integer page);

    Optional<Comment> getComment(Long id);

    Comment addComment(String text);
}
