package ru.pankov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.pankov.entity.Comment;
import ru.pankov.err.ResourceNotFoundException;
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

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentService.getComment(id).orElseThrow(() -> new ResourceNotFoundException("Comment doesn't exist"));
    }

    @PostMapping
    public Comment addComment(@RequestBody String text) {
        return commentService.addComment(text);
    }
}
