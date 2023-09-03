package com.forums.forum.service;

import com.forums.forum.dto.UserProfile;
import com.forums.forum.dto.UserWithStats;
import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.IllegalUserNameException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.Post;
import com.forums.forum.model.Role;
import com.forums.forum.model.User;
import com.forums.forum.repo.PostRepository;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public List<User> searchUserByUserName(String userName) {

        List<User> result = new ArrayList<>();
        List<User> matchingUsers = userRepository.findAllByUsername(userName);
        List<User> containingUsers = userRepository.findAllByUsernameContaining(userName);
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

    public User byId(Long id) throws ResourceNotFoundException{
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User with id "+id+" not found"));
    }

    public User byUserName(String userName) {
        return userRepository.findByUsername(userName).orElse(null);
    }

    @Transactional
    public User updateUser(Long userId, String userName, LocalDate dob, String profileUrl, Gender gender)
            throws IllegalUserNameException, ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        if (userName != null && userName.length() > 0 && !Objects.equals(userName, user.getUsername())) {
            if (userRepository.existsByUsername(userName)) {
                throw new IllegalUserNameException("User name already exist");
            }
            user.setUsername(userName);
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

    public UserProfile getUserProfile(Long userId, int page) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        UserProfile userProfile = new UserProfile();

        List<Post> posts = postRepository.findAllByUser(user, PageRequest.of(page, 10, Sort.by("createdTimeStamp").descending()));

        UserWithStats userWithStats = modelMapper.map(user, UserWithStats.class);

        userProfile.setUser(userWithStats);
        userProfile.setPagePosts(posts);
        userProfile.setPage(page);

        return userProfile;
    }

}
