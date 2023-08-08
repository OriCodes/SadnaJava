package com.forums.forum.controller;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserAlreadyLikeException;
import com.forums.forum.model.Post;
import com.forums.forum.model.PostLike;
import com.forums.forum.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;


    @PostMapping(path = "/addPost")
    public @ResponseBody Post addPost(
            @RequestParam Long userId,
            @RequestParam Long topicId,
            @RequestParam String title,
            @RequestParam String text) throws ResourceNotFoundException {

        return postService.addPost(userId, topicId, title, text);
    }

    @PostMapping(path = "/likePost")
    public @ResponseBody PostLike likePost(Long userId, Long postId) throws ResourceNotFoundException, UserAlreadyLikeException {
        return postService.likePost(userId,postId);
    }

    @GetMapping(path = "/allPosts")
    public @ResponseBody List<Post> allPosts() {
        return postService.allPosts();
    }

    @GetMapping(path = "/byId/{postId}")
    public @ResponseBody Post byId(@PathVariable("postId") Long postId){
        return postService.byId(postId);
    }

    @GetMapping(path = "/numberOfLikes")
    public @ResponseBody int numberOfLikes(Long postId) throws ResourceNotFoundException{
        return postService.getNumberOfLikes(postId);
    }

    @GetMapping(path = "/hasLiked")
    public @ResponseBody boolean hasLiked(Long postId,Long userId) throws ResourceNotFoundException{
        return postService.hasLiked(userId,postId);
    }

    @GetMapping(path ="/allByTopic" )
    public @ResponseBody List<Post>getAllByTopic(Long topicId) throws ResourceNotFoundException {
        return postService.allByTopic(topicId);
    }

    @GetMapping(path ="/allByUser" )
    public @ResponseBody List<Post>getAllByUser(Long userId) throws ResourceNotFoundException {
        return postService.allByUser(userId);
    }

    @GetMapping(path = "/searchPost", params = {"title"})
    public @ResponseBody List<Post>searchPost(String title){
        return postService.search(title);
    }

    @GetMapping(path = "/searchPost", params = {"title","topicId"})
    public @ResponseBody List<Post>searchPost(String title, Long topicId) throws ResourceNotFoundException{
        return postService.searchInTopic(title, topicId);
    }

    @GetMapping(path = "/existByTitle", params = {"title"})
    public @ResponseBody boolean existByTitle(String title){
        return postService.isExistByTitle(title);
    }

    @GetMapping(path = "/existByTitle", params = {"title","topicId"})
    public @ResponseBody boolean existByTitle(String title, Long topicId) throws ResourceNotFoundException{
        return postService.isExistByTitleAndTopic(title, topicId);
    }
}
