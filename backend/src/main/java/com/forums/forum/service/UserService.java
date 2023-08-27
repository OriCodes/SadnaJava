package com.forums.forum.service;

import com.forums.forum.dto.UserProfile;
import com.forums.forum.exception.UserNameAlreadyExistException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.Post;
import com.forums.forum.model.Role;
import com.forums.forum.model.User;
import com.forums.forum.repo.PostRepository;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public User registerUser(String userName, LocalDate dob, String profileUrl, Gender gender, String password) throws UserNameAlreadyExistException {
        if (userRepository.existsByUserName(userName)) {
            throw new UserNameAlreadyExistException();
        }
        User newUser = new User(userName, dob, profileUrl, gender, password);
        newUser.setRole(Role.USER);
        return userRepository.save(newUser);
    }

    public List<User> searchUserByUserName(String userName) {

        List<User> result = new ArrayList<>();
        List<User> matchingUsers = userRepository.findAllByUserName(userName);
        List<User> containingUsers = userRepository.findAllByUserNameContaining(userName);
        if (matchingUsers != null) {
            result.addAll(matchingUsers);
        }

        if (containingUsers != null) {
            for (User user : containingUsers) {
                if (!user.getUsername().equals(userName)) { //not adding twice
                    result.add(user);
                }
            }
        }
        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User byId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User byUserName(String userName) {
        return userRepository.findByUserName(userName).orElse(null);
    }

    @Transactional
    public User updateUser(Long userId, String userName, LocalDate dob, String profileUrl, Gender gender) throws UserNameAlreadyExistException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        if (userName != null && userName.length() > 0 && !Objects.equals(userName, user.getUsername())) {
            if (userRepository.existsByUserName(userName)) {
                throw new UserNameAlreadyExistException();
            }
            user.setUserName(userName);
        }

        if (dob != null && !Objects.equals(dob, user.getDob())) {
            user.setDob(dob);
        }

        if (profileUrl != null && profileUrl.length() > 0 && !Objects.equals(profileUrl, user.getProfileUrl())) {
            user.setProfileUrl(profileUrl);
        }

        if (gender != null && !Objects.equals(gender, user.getGender())) {
            user.setGender(gender);
        }
        return user;
    }

    public UserProfile getUserProfile(Long userId, int page) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        UserProfile userProfile = new UserProfile();

        List<Post> posts = postRepository.findAllByUser(user, PageRequest.of(page, 10, Sort.by("createdTimeStamp").descending()));

        userProfile.setUser(user);
        userProfile.setPagePosts(posts);
        userProfile.setPage(page);

        return userProfile;
    }
}
