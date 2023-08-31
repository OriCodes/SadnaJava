package com.forums.forum.controller;

import com.forums.forum.dto.NewCommentBody;
import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserActionNotAllowedException;
import com.forums.forum.model.Comment;
import com.forums.forum.model.CommentLike;
import com.forums.forum.model.User;
import com.forums.forum.service.CommentService;
import com.forums.forum.service.DeleteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final DeleteService deleteService;

    @DeleteMapping(path = "/deleteComment")
    public void deleteComment(Long commentId, @AuthenticationPrincipal User user) throws ResourceNotFoundException, UserActionNotAllowedException {
        deleteService.deleteComment(commentId, user.getUserId());
    }

    @DeleteMapping(path = "/unlikeComment")
    public void unlikeComment(Long commentId, @AuthenticationPrincipal User user) throws ResourceNotFoundException, UserActionNotAllowedException {
        deleteService.unlikeComment(commentId, user.getUserId());
    }

    @PostMapping(path = "/addComment")
    public @ResponseBody Comment addComment(Long postId, @RequestBody NewCommentBody body, @AuthenticationPrincipal User user) throws ResourceNotFoundException {
        return commentService.addComment(user.getUserId(), postId, body.getText());
    }

    @PostMapping(path = "/likeComment")
    public @ResponseBody CommentLike likeComment(Long commentId, @AuthenticationPrincipal User user) throws ResourceNotFoundException, UserActionNotAllowedException {
        return commentService.likeComment(user.getUserId(), commentId);
    }
}

