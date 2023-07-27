package com.forums.forum.service;

import com.forums.forum.exception.AlreadyFollowsException;
import com.forums.forum.model.Follow;
import com.forums.forum.model.User;
import com.forums.forum.repo.FollowRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;

    public boolean isFollowing(User follower, User followed){
        return followRepository.existsByFollowerAndFollowed(follower, followed);
    }

    public int getNumberOfFollowers(User user){
        return followRepository.countAllByFollowed(user);
    }

    public int getNumberOfFollowed(User user){
        return followRepository.countAllByFollower(user);
    }

    public Follow byFollowAndFollower(User follower, User followed){
        return followRepository.findByFollowerAndAndFollowed(follower,followed);
    }

    public List<Follow> getFollowers(User user){
        return followRepository.findAllByFollowed(user);
    }

    public List<Follow> getFollowed(User user){
        return followRepository.findAllByFollower(user);
    }

    public Follow addFollow(User follower, User followed, Timestamp timestamp) throws AlreadyFollowsException {
        if (followRepository.existsByFollowerAndFollowed(follower,followed))
            throw new AlreadyFollowsException();

        Follow newFollow = new Follow(follower,followed,timestamp);
        return followRepository.save(newFollow);
    }
}
