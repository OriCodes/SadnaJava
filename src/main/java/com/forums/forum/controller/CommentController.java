package com.forums.forum.controller;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserActionNotAllowedException;
import com.forums.forum.model.Comment;
import com.forums.forum.model.CommentLike;
import com.forums.forum.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(path = "/addComment")
    public @ResponseBody Comment addComment(Long userId,Long postId,String text) throws ResourceNotFoundException{
        return commentService.addComment(userId, postId, text);
    }

    @PostMapping(path = "/likeComment")
    public @ResponseBody CommentLike likeComment(Long userId,Long commentId) throws  ResourceNotFoundException, UserActionNotAllowedException{
        return commentService.likeComment(userId, commentId);
    }

    @GetMapping(path = "/commentsForPost")
    public  @ResponseBody List<Comment> commentsForPost(Long postId) throws ResourceNotFoundException{
        return commentService.getAllCommentsForPost(postId);
    }

    @GetMapping(path = "/commentsByUser")
    public  @ResponseBody List<Comment> commentsByUser(Long userId) throws ResourceNotFoundException{
        return commentService.getAllCommentsByUser(userId);
    }

    @GetMapping(path = "/postCommentCounter")
    public @ResponseBody int postCommentCounter(Long postId) throws ResourceNotFoundException{
        return commentService.getNumberOfCommentsForPost(postId);
    }

    @GetMapping(path = "/userCommentCounter")
    public @ResponseBody int userCommentCounter(Long userId) throws ResourceNotFoundException{
        return commentService.getNumberOfCommentsByUser(userId);
    }

    @GetMapping(path = "/commentLikeCounter")
    public @ResponseBody int commentLikeCounter(Long commentId) throws ResourceNotFoundException{
        return commentService.getNumberOfLikes(commentId);
    }

    @GetMapping(path = "/hasLikedComment")
    public @ResponseBody boolean hasLikedComment(Long userId ,Long commentId) throws ResourceNotFoundException{
        return commentService.hasLiked(userId, commentId);
    }
}
