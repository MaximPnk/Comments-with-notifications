package ru.pankov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.pankov.dao.CommentRepository;
import ru.pankov.dao.NotificationRepository;
import ru.pankov.entity.Comment;
import ru.pankov.service.inter.CommentService;
import ru.pankov.service.inter.NotificationService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
class CommentControllerTestMockMvc {

    int count = 1000;

    @Autowired
    MockMvc mock;

    @Autowired
    WebApplicationContext context;

    @Autowired
    CommentService commentService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    NotificationRepository notificationRepository;

    @Before
    public void setup() {
        mock = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void add() throws Exception {
        for (int i = 0; i < 1000; i++) {
            Comment comment = new Comment("" + i);
            String json = new ObjectMapper().writeValueAsString(comment);
            mock.perform(post("/api/comments")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(json))
                    .andExpect(status().isOk());
        }

        Thread.sleep(15000);

        log.info(String.format("Success chance of adding a comment = %f", (double) commentRepository.count() / 1000));
        log.info(String.format("Success chance of delivering a notification = %f", (double) notificationRepository.countAllByDelivered(true) / notificationRepository.count()));

    }
}