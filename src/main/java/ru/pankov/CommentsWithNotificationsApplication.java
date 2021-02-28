package ru.pankov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class CommentsWithNotificationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentsWithNotificationsApplication.class, args);
	}

}
