package com.forums.forum.controller;

import com.forums.forum.dto.UserProfile;
import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.IllegalUserNameException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.service.DeleteService;
import com.forums.forum.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping(path = "api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final DeleteService deleteService;

    @DeleteMapping(path = "/deleteUser/{userId}")
    public void deleteUser(@PathVariable("userId") Long userId) throws ResourceNotFoundException {
        deleteService.deleteUser(userId);
    }

    @PutMapping(path = "{userId}")
    public @ResponseBody User updateUser(
            @PathVariable("userId") Long userId,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
            @RequestParam(required = false) String profileUrl,
            @RequestParam(required = false) Gender gender
    ) throws IllegalUserNameException, ResourceNotFoundException {
        return userService.updateUser(userId, userName, dob, profileUrl, gender);
    }


    @GetMapping(path = "/allUsers")
    public @ResponseBody List<User> getUsers() {
        return userService.allUsers();
    }

    @GetMapping(path = "byId/{userId}")
    public @ResponseBody User byId(@PathVariable("userId") Long userId) throws ResourceNotFoundException {
        return userService.byId(userId);
    }

    @GetMapping(path = "byUserName/{userName}")
    public @ResponseBody User byUserName(@PathVariable("userName") String userName) {
        return userService.byUserName(userName);
    }

    @GetMapping(path = "/searchUser")
    public @ResponseBody List<User> searchByUserName(@RequestParam String userName) {
        return userService.searchUserByUserName(userName);
    }

    @GetMapping(path = "/currentUser")
    public @ResponseBody User currentUser(@AuthenticationPrincipal User user) {
        return userService.byUserName(user.getUsername());
    }

    @GetMapping(path = "/getUserProfile/{userId}")
    public @ResponseBody UserProfile getUserProfile(@PathVariable("userId") Long userId,
                                                    @RequestParam(defaultValue = "0") Integer page) throws ResourceNotFoundException {
        return userService.getUserProfile(userId, page);
    }
}
