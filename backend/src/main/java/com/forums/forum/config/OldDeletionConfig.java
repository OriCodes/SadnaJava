package com.forums.forum.config;

import com.forums.forum.service.DeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class OldDeletionConfig {

    private final DeleteService deleteService;

    @Autowired
    public OldDeletionConfig(DeleteService deleteService) {
        this.deleteService = deleteService;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void deleteOld() {
        System.out.println("Deleting old posts");

        deleteService.deleteOldPosts();


    }
}
