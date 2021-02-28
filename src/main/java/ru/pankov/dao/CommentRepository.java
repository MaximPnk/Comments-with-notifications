package ru.pankov.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pankov.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findByText(String text);
}
