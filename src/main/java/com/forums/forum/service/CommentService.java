package com.forums.forum.service;

import com.forums.forum.model.Comment;
import com.forums.forum.model.Post;
import com.forums.forum.model.User;
import com.forums.forum.repo.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment byId(Long id){
        return commentRepository.findById(id).orElse(null);
    }

    public List<Comment> getAllCommentsForPost(Post post){
        return commentRepository.findAllByPost(post);
    }

    public List<Comment> getAllCommentsByUser(User user){
        return commentRepository.findAllByUser(user);
    }

    public int getNumberOfCommentsForPost(Post post) {
        return  commentRepository.countAllByPost(post);
    }

    public int getNumberOfCommentsByUser(User user) {
        return  commentRepository.countAllByUser(user);
    }

    public Comment addComment(User user, Post post, String text, Timestamp timestamp){
        Comment comment = new Comment(text,timestamp,user,post);
        return commentRepository.save(comment);
    }
}
