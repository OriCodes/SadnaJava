package com.forums.forum.service;

import com.forums.forum.model.*;
import com.forums.forum.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
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

    @Transactional
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

    @Transactional
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

    @Transactional
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
    @Transactional
    public void deletePost(Long postId, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post with id " + postId + " not found"));

        if (post.getUser().getUserId() != user.getUserId()){
            throw new IllegalArgumentException("User with id " + userId + " isn't the publisher of post with id " + postId);
        }

        deletePost(post);

    }

    @Transactional
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        postLikeRepository.deleteAllByUser(user);
        commentLikeRepository.deleteAllByUser(user);
        followRepository.deleteAllByFollower(user);
        followRepository.deleteAllByFollowed(user);
        messageRepository.deleteAllByReceiver(user);
        messageRepository.deleteAllBySender(user);


        for (Comment comment : commentRepository.findAllByUser(user)){
            deleteComment(comment);
        }

        for (Post post : postRepository.findAllByUser(user)){
            deletePost(post);
        }

        userRepository.delete(user);

    }
    @Transactional
    public void deleteTopic(Long topicId){
       Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic with id " + topicId + " not found"));

       for (Post post : postRepository.findAllByTopic(topic)){
           deletePost(post);
       }
       topicRepository.delete(topic);
    }

    private void deleteComment(Comment comment){
        commentLikeRepository.deleteAllByComment(comment);
        commentRepository.delete(comment);
    }

    private void deletePost(Post post){
        postLikeRepository.deleteAllByPost(post);
        for (Comment comment : commentRepository.findAllByPost(post)){
            deleteComment(comment);
        }
        postRepository.delete(post);
    }


}
