package com.forums.forum.service;

import com.forums.forum.exception.AlreadyFollowsException;
import com.forums.forum.model.Follow;
import com.forums.forum.model.User;
import com.forums.forum.repo.FollowRepository;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Service
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public Follow byId(Long id){
        return followRepository.findById(id).orElse(null);
    }
    public boolean isFollowing(Long followerId, Long followedId){
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followedId + " not found"));

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followerId + " not found"));

        return followRepository.existsByFollowerAndFollowed(follower, followed);
    }

    public int getNumberOfFollowers(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        return followRepository.countAllByFollowed(user);
    }

    public int getNumberOfFollowed(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        return followRepository.countAllByFollower(user);
    }

    public Follow byFollowAndFollower(Long followerId, Long followedId){
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followedId + " not found"));

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followerId + " not found"));

        return followRepository.findByFollowerAndAndFollowed(follower,followed);
    }

    public List<Follow> getFollowers(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        return followRepository.findAllByFollowed(user);
    }

    public List<Follow> getFollowed(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        return followRepository.findAllByFollower(user);
    }

    public Follow addFollow(Long followerId, Long followedId, Timestamp timestamp){
        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followedId + " not found"));

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + followerId + " not found"));

        if (follower.equals(followed)) {
            throw new IllegalArgumentException("Follower and followed cannot be the same user.");
        }

        if (followRepository.existsByFollowerAndFollowed(follower, followed)) {
            throw new IllegalArgumentException("User with id " + followerId + " already follow User with id " + followedId);
        }

        Follow newFollow = new Follow(follower,followed,timestamp);
        return followRepository.save(newFollow);
    }

}
