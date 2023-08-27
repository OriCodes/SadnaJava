package com.forums.forum.service;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserActionNotAllowedException;
import com.forums.forum.model.Comment;
import com.forums.forum.model.CommentLike;
import com.forums.forum.model.Post;
import com.forums.forum.model.User;
import com.forums.forum.repo.CommentLikeRepository;
import com.forums.forum.repo.CommentRepository;
import com.forums.forum.repo.PostRepository;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    public Comment byId(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> getAllCommentsForPost(Long postId) throws ResourceNotFoundException {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        return commentRepository.findAllByPost(post);
    }

    public List<Comment> getAllCommentsByUser(Long userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        return commentRepository.findAllByUser(user);
    }

    public int getNumberOfCommentsForPost(Long postId) throws ResourceNotFoundException{
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        return commentRepository.countAllByPost(post);
    }

    public int getNumberOfCommentsByUser(Long userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        return commentRepository.countAllByUser(user);
    }

    public Comment addComment(Long userId, Long postId, String text) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        Comment comment = new Comment(text, user, post);
        return commentRepository.save(comment);
    }

    public int getNumberOfLikes(Long commentId) throws ResourceNotFoundException {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));

        return commentLikeRepository.countAllByComment(comment);
    }

    public boolean hasLiked(Long userId, Long commentId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));

        return commentLikeRepository.existsByCommentAndUser(comment, user);
    }

    public CommentLike likeComment(Long userId, Long commentId) throws ResourceNotFoundException, UserActionNotAllowedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " not found"));

        if (commentLikeRepository.existsByCommentAndUser(comment, user)) {
            throw new UserActionNotAllowedException("User with id " + userId + " already like comment with id " + commentId);
        }

        CommentLike commentLike = new CommentLike(user, comment);
        return commentLikeRepository.save(commentLike);
    }
}
