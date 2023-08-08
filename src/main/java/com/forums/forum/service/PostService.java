package com.forums.forum.service;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserAlreadyLikeException;
import com.forums.forum.model.Post;
import com.forums.forum.model.PostLike;
import com.forums.forum.model.Topic;
import com.forums.forum.model.User;
import com.forums.forum.repo.PostLikeRepository;
import com.forums.forum.repo.PostRepository;
import com.forums.forum.repo.TopicRepository;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    public Post byId(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public boolean isExistByTitle(String title) {
        return postRepository.existsByTitle(title);
    }

    public boolean isExistByTitleAndTopic(String title, Long topicId) throws ResourceNotFoundException{
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

        return postRepository.existsByTitleAndTopic(title, topic);
    }

    public List<Post> allPosts() {
        return postRepository.findAll();
    }

    public List<Post> search(String query) {
        List<Post> result = new ArrayList<>();
        List<Post> perfectMatch = postRepository.findAllByTitle(query);
        List<Post> imperfectMatch = postRepository.findAllByTitleContaining(query);

        return mergeSearchResult(query, result, perfectMatch, imperfectMatch);
    }

    public List<Post> searchInTopic(String query, Long topicId) throws ResourceNotFoundException {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

        List<Post> result = new ArrayList<>();
        List<Post> perfectMatch = postRepository.findAllByTopicAndTitle(topic, query);
        List<Post> imperfectMatch = postRepository.findAllByTopicAndTitleContaining(topic, query);

        return mergeSearchResult(query, result, perfectMatch, imperfectMatch);
    }

    public List<Post> allByUser(Long userId) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        return postRepository.findAllByUser(user);
    }

    public List<Post> allByTopic(Long topicId) throws ResourceNotFoundException{
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

        return postRepository.findAllByTopic(topic);
    }

    public Post addPost(Long userId, Long topicId, String title, String text) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic with id " + topicId + " not found"));

        Post newPost = new Post(title, text, user, topic);
        return postRepository.save(newPost);
    }


    public int getNumberOfLikes(Long postId) throws ResourceNotFoundException{
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        return postLikeRepository.countAllByPost(post);
    }

    public boolean hasLiked(Long userId, Long postId) throws ResourceNotFoundException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        return postLikeRepository.existsByPostAndUser(post, user);
    }

    public PostLike likePost(Long userId, Long postId) throws ResourceNotFoundException, UserAlreadyLikeException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

        if (postLikeRepository.existsByPostAndUser(post, user))
            throw new UserAlreadyLikeException("User with id " + userId + " already like post with id " + postId);

        return postLikeRepository.save(new PostLike(user, post));
    }


    private List<Post> mergeSearchResult(String query, List<Post> result, List<Post> perfectMatch, List<Post> imperfectMatch) {
        if (perfectMatch != null) {
            result.addAll(perfectMatch);
        }

        if (imperfectMatch != null) {
            Iterator<Post> iterator = imperfectMatch.iterator();
            while (iterator.hasNext()) {
                Post post = iterator.next();
                if (!post.getTitle().equals(query)) { //not adding twice
                    result.add(post);
                }
            }
        }

        return result;
    }

}