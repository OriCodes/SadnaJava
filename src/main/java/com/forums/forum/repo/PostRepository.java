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

    Post findByTitleName(String title);

    List<Post> findAllByTitle(String title);
    List<Post> findAllByTitleContaining(String seq);

    List<Post> findAllByUser(User user);
    List<Post> findAllByTopic(Topic topic);

}
