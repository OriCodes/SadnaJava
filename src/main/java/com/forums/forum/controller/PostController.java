package com.forums.forum.controller;

import com.forums.forum.model.Post;
import com.forums.forum.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(path = "api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping(path = "/allPosts")
    public @ResponseBody List<Post>allPosts(){return postService.allPosts();}

    @PostMapping(path = "/addPost")
    public @ResponseBody Post addPost(
            @RequestParam Long userId,
            @RequestParam Long topicId,
            @RequestParam String title,
            @RequestParam String text){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return postService.addPost(userId, topicId, title, text, timestamp);
    }
}
