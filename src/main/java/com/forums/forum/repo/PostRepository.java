package com.forums.forum.repo;

import com.forums.forum.model.Post;
import com.forums.forum.model.Topic;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    boolean existsByTitle(String title);
    boolean existsByTitleAndTopic(String title, Topic topic);

    Post findByPostId(Long postId);
    Post findByTitle(String title);
    Post findByTitleAndTopic(String title, Topic topic);

    List<Post> findAllByTopicAndTitleContaining(Topic topic, String seq);
    List<Post> findAllByTopicAndTitle(Topic topic, String title);
    List<Post> findAllByTitle(String title);
    List<Post> findAllByTitleContaining(String seq);
    List<Post> findAllByUser(User user);
    List<Post> findAllByTopic(Topic topic);
    List<Post> findAllByTopicAndUser(Topic topic, User user);

}
