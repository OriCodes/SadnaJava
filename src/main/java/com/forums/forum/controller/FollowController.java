package com.forums.forum.controller;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserActionNotAllowedException;
import com.forums.forum.model.Follow;
import com.forums.forum.service.DeleteService;
import com.forums.forum.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/follows")
@AllArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final DeleteService deleteService;

    @DeleteMapping(path = "/unfollow")
    public void unfollow(Long followerId, Long followedId) throws ResourceNotFoundException, UserActionNotAllowedException
    {
        deleteService.unfollow(followerId, followedId);
    }

    @PostMapping(path="/follow")
    public @ResponseBody Follow follow(Long followerId, Long followedId) throws ResourceNotFoundException, UserActionNotAllowedException {
        return followService.addFollow(followerId,followedId);
    }

    @GetMapping(path = "/followers/{userId}")
    public @ResponseBody List<Follow>getFollowers(@PathVariable("userId")Long userId) throws ResourceNotFoundException {
        return followService.getFollowers(userId);
    }

    @GetMapping(path = "/following/{userId}")
    public @ResponseBody List<Follow>getFollowings(@PathVariable("userId")Long userId) throws ResourceNotFoundException {
        return followService.getFollowed(userId);
    }

    @GetMapping(path = "/followers-count/{userId}")
    public @ResponseBody int getNumberOfFollowers(@PathVariable("userId")Long userId) throws ResourceNotFoundException{
        return followService.getNumberOfFollowers(userId);
    }

    @GetMapping(path = "/following-count/{userId}")
    public @ResponseBody int getNumberOfFollowing(@PathVariable("userId")Long userId) throws ResourceNotFoundException{
        return followService.getNumberOfFollowed(userId);
    }

    @GetMapping(path = "/isFollowing")
    public @ResponseBody boolean isFollowing(Long followerId, Long followedId) throws ResourceNotFoundException{
        return followService.isFollowing(followerId, followedId);
    }

    @GetMapping(path = "/byFollowerAndFollowed")
    public @ResponseBody Follow byFollowAndFollower(Long followerId, Long followedId)throws ResourceNotFoundException{
        return followService.byFollowAndFollower(followerId, followedId);
    }

    @GetMapping(path = "/byId/{followId}")
    public @ResponseBody Follow byId(@PathVariable("followId")Long followId){
        return followService.byId(followId);
    }


}
