package com.forums.forum.service;

import com.forums.forum.model.Comment;
import com.forums.forum.model.Message;
import com.forums.forum.model.Post;
import com.forums.forum.model.User;
import com.forums.forum.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class DeleteService {

    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;
    private final MessageRepository messageRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void unfollow(Long followerId, Long followedId){

        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followedId + " not found"));

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followerId + " not found"));

        if(!followRepository.existsByFollowerAndFollowed(follower,followed)){
            throw new IllegalArgumentException("User with id " + followerId + " don't follow User with id " + followedId);
        }

        followRepository.deleteAllByFollowerAndAndFollowed(follower,followed);
    }

    public void unlikePost(Long postId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " not found"));

        if (!postLikeRepository.existsByPostAndUser(post,user)){
            throw new IllegalArgumentException("User with id " + userId + " don't like post with id " + postId);
        }

        postLikeRepository.deleteAllByPostAndUser(post,user);
    }

    public void unlikeComment(Long commentId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id " + commentId + " not found"));

        if (!commentLikeRepository.existsByCommentAndUser(comment,user)){
            throw new IllegalArgumentException("User with id " + userId + " don't like comment with id " + commentId);
        }

        commentLikeRepository.deleteAllByCommentAndUser(comment,user);
    }

    public void deleteComment(Long commentId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment with id " + commentId + " not found"));

        if (comment.getUser().getUserId() != user.getUserId()){
            throw new IllegalArgumentException(
                    "User with id " + userId + " isn't the publisher of comment with id " + commentId
            );
        }

        deleteComment(comment);
    }

    private void deleteComment(Comment comment){
        commentLikeRepository.deleteAllByComment(comment);
        commentRepository.delete(comment);
    }
}
