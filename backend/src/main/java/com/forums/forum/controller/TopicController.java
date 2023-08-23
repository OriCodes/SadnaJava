package com.forums.forum.controller;

import com.forums.forum.model.Topic;
import com.forums.forum.service.TopicService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/topics")
@AllArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @GetMapping(path = "/allTopics")
    public @ResponseBody List<Topic> allTopics() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(authentication.getPrincipal());
        return topicService.allTopics();
    }

    @GetMapping(path = "/allTopics", params = "seq")
    public @ResponseBody List<Topic> allTopics(String seq) {
        return topicService.allTopicsContains(seq);
    }

    @GetMapping(path = "/byId/{topicId}")
    public @ResponseBody Topic byId(@PathVariable("topicId") Long topicId) {
        return topicService.byId(topicId);
    }

    @GetMapping(path = "/{topicName}")
    public @ResponseBody Topic byTopicName(@PathVariable("topicName") String topicName) {
        return topicService.byTopicName(topicName);
    }

    @GetMapping(path = "/existByTitle")
    public @ResponseBody boolean byId(String title) {
        return topicService.topicExistByTitle(title);
    }
}
