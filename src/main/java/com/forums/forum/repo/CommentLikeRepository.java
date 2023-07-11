package com.forums.forum.repo;

import com.forums.forum.model.Comment;
import com.forums.forum.model.CommentLike;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    int countAllByComment(Comment comment);
    boolean existsByCommentAndUser(Comment comment, User user);
}
