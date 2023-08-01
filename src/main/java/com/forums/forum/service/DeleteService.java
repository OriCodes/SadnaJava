package com.forums.forum.service;

import com.forums.forum.model.Comment;
import com.forums.forum.model.Message;
import com.forums.forum.model.Post;
import com.forums.forum.model.User;
import com.forums.forum.repo.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

        User follower = userRepository.findByUserId(followerId);
        User followed = userRepository.findByUserId(followedId);

        if (followed == null || follower == null){
            throw new IllegalArgumentException("Follower or Followed don't exist");
        }

        if(!followRepository.existsByFollowerAndFollowed(follower,followed)){
            throw new IllegalArgumentException("User with id " + followerId + " don't follow User with id " + followedId);
        }

        followRepository.deleteAllByFollowerAndAndFollowed(follower,followed);
    }

    public void unlikePost(Long postId, Long userId){
        User user = userRepository.findByUserId(userId);
        Post post = postRepository.findByPostId(postId);

        if (post == null || user == null){
            throw new IllegalArgumentException("Post or user don't exist");
        }

        if (!postLikeRepository.existsByPostAndUser(post,user)){
            throw new IllegalArgumentException("User with id " + userId + " don't like post with id " + postId);
        }

        postLikeRepository.deleteAllByPostAndUser(post,user);
    }

    public void unlikeComment(Long commentId, Long userId){
        User user = userRepository.findByUserId(userId);
        Comment comment = commentRepository.findByCommentId(commentId);

        if (comment == null || user == null){
            throw new IllegalArgumentException("Comment or User don't exist");
        }

        if (!commentLikeRepository.existsByCommentAndUser(comment,user)){
            throw new IllegalArgumentException("User with id " + userId + " don't like comment with id " + commentId);
        }

        commentLikeRepository.deleteAllByCommentAndUser(comment,user);
    }
}
