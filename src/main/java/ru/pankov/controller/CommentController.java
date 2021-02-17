package ru.pankov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pankov.entity.Comment;
import ru.pankov.service.inter.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<Comment> getComments(@RequestParam(value = "page", defaultValue = "1") Integer page) {
        return commentService.getComments(page).getContent();
    }

    @PostMapping
    public Comment addComment(@RequestBody String text) {
        return commentService.addComment(text);
    }
}
