package com.forums.forum.service;

import com.forums.forum.model.Post;
import com.forums.forum.model.Topic;
import com.forums.forum.model.User;
import com.forums.forum.repo.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post byId(Long id){
        return postRepository.findById(id).orElse(null);
    }

    public Post byTitle(String title){
        return postRepository.findByTitle(title);
    }

    public Post byTitleAndTopic(String title, Topic topic){
        return postRepository.findByTitleAndTopic(title, topic);
    }

    public boolean isExistByTitle(String title){
        return postRepository.existsByTitle(title);
    }

    public boolean isExistByTitleAndTopic(String title, Topic topic){
        return postRepository.existsByTitleAndTopic(title, topic);
    }

    public List<Post> allPosts()
    {
        return postRepository.findAll();
    }

    public List<Post> allByTitle(String title){
        return  postRepository.findAllByTitle(title);
    }
    public List<Post> allByTitleContaining(String seq){
        return postRepository.findAllByTitleContaining(seq);
    }

    public List<Post> allByTopicAndTitleContaining(Topic topic, String seq){
        return postRepository.findAllByTopicAndTitleContaining(topic, seq);
    }

    public  List<Post> allByTopicAndTitle(Topic topic, String title){
        return postRepository.findAllByTopicAndTitle(topic, title);
    }

    public List<Post> allByUser(User user){
        return postRepository.findAllByUser(user);
    }

    public List<Post> allByTopic(Topic topic){
        return postRepository.findAllByTopic(topic);
    }

    public List<Post> allByUserAndTopic(User user, Topic topic){
        return postRepository.findAllByTopicAndUser(topic, user);
    }

    public Post addPost(User user, Topic topic, String title, String text, Timestamp timestamp)
    {
        Post newPost = new Post(title,text, timestamp, user, topic);
        return postRepository.save(newPost);
    }


}
