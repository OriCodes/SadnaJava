package com.forums.forum.service;

import com.forums.forum.exception.UserNameAlreadyExistException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(String userName, LocalDate dob, String profileUrl, Gender gender, String auth0Id) throws UserNameAlreadyExistException
    {
        if(userRepository.existsByUserName(userName)){
            throw new UserNameAlreadyExistException();
        }
        User newUser = new User(userName, dob,  profileUrl, gender,  auth0Id);
        return userRepository.save(newUser);
    }

    public List<User> searchUserByUserName(String userName)
    {

        List<User> result = new ArrayList<>();
        List<User> matchingUsers = userRepository.findAllByUserName(userName);
        List<User> containingUsers = userRepository.findAllByUserNameContaining(userName);
        if(matchingUsers != null) {
            result.addAll(matchingUsers);
        }

        if (containingUsers != null){
            for (User user : containingUsers) {
                if (!user.getUserName().equals(userName)) { //not adding twice
                    result.add(user);
                }
            }
        }
        if (result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public User byId(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User byUserName(String userName){return userRepository.findByUserName(userName);}

    @Transactional
    public User updateUser(Long userId, String userName, LocalDate dob, String profileUrl, Gender gender) throws UserNameAlreadyExistException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + userId + " not found"));

        if(userName != null && userName.length() > 0 && !Objects.equals(userName,user.getUserName())){
            if(userRepository.existsByUserName(userName)){
                throw new UserNameAlreadyExistException();
            }
            user.setUserName(userName);
        }

        if(dob != null && !Objects.equals(dob,user.getDob())){
            user.setDob(dob);
        }

        if(profileUrl != null && profileUrl.length() > 0 && !Objects.equals(profileUrl,user.getProfileUrl())){
            user.setProfileUrl(profileUrl);
        }

        if (gender != null && !Objects.equals(gender,user.getGender())){
            user.setGender(gender);
        }
        return user;
    }


}
