package com.forums.forum.repo;

import com.forums.forum.model.Post;
import com.forums.forum.model.PostLike;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    int countAllByPost(Post post);

    boolean existsByPostAndUser(Post post, User user);
}
