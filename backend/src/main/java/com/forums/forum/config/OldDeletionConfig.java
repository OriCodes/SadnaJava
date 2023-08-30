package com.forums.forum.config;

import com.forums.forum.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class OldDeletionConfig {

    private final PostService postService;

    @Autowired
    public OldDeletionConfig(PostService postService) {
        this.postService = postService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void deleteOld() {
        System.out.println("Deleting old posts");

        postService.deleteOldPosts();


    }
}
