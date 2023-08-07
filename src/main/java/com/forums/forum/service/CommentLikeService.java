package com.forums.forum.service;

import com.forums.forum.model.Comment;
import com.forums.forum.model.CommentLike;
import com.forums.forum.model.User;
import com.forums.forum.repo.CommentLikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;

    public int getNumberOfLikes(Comment comment) {
        return commentLikeRepository.countAllByComment(comment);
    }

    public boolean hasLiked(User user, Comment comment) {
        return commentLikeRepository.existsByCommentAndUser(comment, user);
    }

    public void deleteLike(Comment comment, User user) {
        commentLikeRepository.deleteAllByCommentAndUser(comment, user);
    }

    public void addLike(User user, Comment comment) {
        CommentLike commentLike = new CommentLike(user, comment);
        commentLikeRepository.save(commentLike);
    }

}
