package com.forums.forum.repo;

import com.forums.forum.model.Comment;
import com.forums.forum.model.Post;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);
    List<Comment> findAllByUser(User user);

    int countAllByPost(Post post);
    int countAllByUser(User user);
}
