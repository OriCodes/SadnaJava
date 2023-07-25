package com.forums.forum.service;

import com.forums.forum.exception.UserNameAlreadyExistException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }


    @Test
    void addUserSuccess() {
        //given
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        String userName = "poseidon";
        Integer age = 19;
        String profileUrl = "Profile_URL";
        Gender gender = Gender.MALE;
        String auth0Id= "auth0Id";
        User savedUser = new User(userName, age, profileUrl, gender, auth0Id);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        //when
        try {
            User newUser = userService.addUser(userName,age,profileUrl,gender,auth0Id);
            //then
            verify(userRepository, times(1)).save(any(User.class));
            assertThat(newUser).isEqualTo(savedUser);
        } catch (UserNameAlreadyExistException e) { // this should never happen
            System.out.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    @Test
    void addUserShouldThrow() {
        //given
        when(userRepository.existsByUserName(anyString())).thenReturn(true);
        String userName = "poseidon";
        Integer age = 19;
        String profileUrl = "Profile_URL";
        Gender gender = Gender.MALE;
        String auth0Id= "auth0Id";

        //then
        assertThatThrownBy(()-> userService.addUser(userName,age,profileUrl,gender,auth0Id)).
                isInstanceOf(UserNameAlreadyExistException.class);
        verify(userRepository, never()).save(any());


    }

}