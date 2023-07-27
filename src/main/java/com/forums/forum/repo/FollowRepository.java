package com.forums.forum.repo;

import com.forums.forum.model.Follow;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {
    int countAllByFollower(User follower);
    int countAllByFollowed(User followed);

    Follow findByFollowerAndAndFollowed(User follower , User followed);

    boolean existsByFollowerAndFollowed(User follower, User followed);

    List<Follow> findAllByFollowed(User followed);
    List<Follow> findAllByFollower(User follower);
}
