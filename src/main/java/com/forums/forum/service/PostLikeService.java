package com.forums.forum.service;

import com.forums.forum.model.Post;
import com.forums.forum.model.PostLike;
import com.forums.forum.model.User;
import com.forums.forum.repo.PostLikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@AllArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public int getNumberOfLikes(Post post){
        return postLikeRepository.countAllByPost(post);
    }

    public boolean hasLiked(User user, Post post){
        return  postLikeRepository.existsByPostAndUser(post,user);
    }

    public void deleteLike(Post post, User user){
        postLikeRepository.deleteAllByPostAndUser(post,user);
    }

    public void addLike(PostLike postLike){
        postLikeRepository.save(postLike);
    }
}
