package com.forums.forum.controller;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserActionNotAllowedException;
import com.forums.forum.model.Follow;
import com.forums.forum.service.FollowService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/follows")
@AllArgsConstructor
public class FollowController {
    private final FollowService followService;


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


}
