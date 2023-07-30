package com.forums.forum.service;

import com.forums.forum.exception.UserNameAlreadyExistException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.FollowRepository;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    public void registerUser(String userName, LocalDate dob, String profileUrl, Gender gender, String auth0Id) throws UserNameAlreadyExistException
    {
        if(userRepository.existsByUserName(userName)){
            throw new UserNameAlreadyExistException();
        }
        User newUser = new User(userName, dob,  profileUrl, gender,  auth0Id);
    }

    public List<User> searchUserByUserName(String userName)
    {

        List<User> result = new ArrayList<>();
        List<User> matchingUsers = userRepository.findAllByUserName(userName);
        List<User> containingUsers = userRepository.findAllByUserName(userName);
        if(matchingUsers != null) {
            result.addAll(matchingUsers);
        }

        if (containingUsers != null){
            Iterator<User> iterator = containingUsers.iterator();
            while (iterator.hasNext()) {
                User user = iterator.next();
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



}
