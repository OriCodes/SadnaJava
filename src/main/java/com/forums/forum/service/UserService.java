package com.forums.forum.service;

import com.forums.forum.exception.UserNameAlreadyExistException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User addUser(String userName, Integer age, String profileUrl, Gender gender, String auth0Id) throws UserNameAlreadyExistException
    {
        if(userRepository.existsByUserName(userName)){
            throw new UserNameAlreadyExistException();
        }
        User newUser = new User(userName, age,  profileUrl, gender,  auth0Id);
        return userRepository.save(newUser);
    }

    public List<User> allUsers(){
        return userRepository.findAll();
    }

    public List<User> byUserNameContainingSequence(String seq){
        return userRepository.findAllByUserNameContaining(seq);
    }

    public User byUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public User byId(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public List<User> allByGender(Gender gender){
        return userRepository.findAllByGender(gender);
    }

}
