package com.forums.forum.service;

import com.forums.forum.exception.UserNameAlreadyExistException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
       // userService = new UserService(userRepository);
    }


    @Test
    public void addUserSuccess() {
        //given
        when(userRepository.existsByUserName(anyString())).thenReturn(false);
        String userName = "poseidon";
        LocalDate dob2 = LocalDate.of(1999, Month.APRIL,7);
        String profileUrl = "Profile_URL";
        Gender gender = Gender.MALE;
        String auth0Id= "auth0Id";
        User savedUser = new User(userName, dob2, profileUrl, gender, auth0Id);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        //when
        try {
            userService.registerUser(userName,dob2,profileUrl,gender,auth0Id);
            //then
            verify(userRepository, times(1)).save(any(User.class));
            //assertThat(newUser).isEqualTo(savedUser);
        } catch (UserNameAlreadyExistException e) { // this should never happen
            System.out.println(e.getStackTrace());
        }
    }

    @Test
    public void addUserShouldThrow() {
        //given
        when(userRepository.existsByUserName(anyString())).thenReturn(true);
        String userName = "poseidon";
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        String profileUrl = "Profile_URL";
        Gender gender = Gender.MALE;
        String auth0Id= "auth0Id";

        //then
        assertThatThrownBy(()-> userService.registerUser(userName,dob,profileUrl,gender,auth0Id)).
                isInstanceOf(UserNameAlreadyExistException.class);
        verify(userRepository, never()).save(any());
    }

    @Test
    public void allUsers(){
        //given
        LocalDate dob1 = LocalDate.of(1999, Month.APRIL,7);
        User user1 = new User("Poseidon", dob1, "URL",Gender.MALE,"auth0Id");
        User user2 = new User("Venus", dob1, "URL",Gender.FEMALE,"auth0Id");
        List<User>expecList = Arrays.asList(user1,user2);
        when(userRepository.findAll()).thenReturn(expecList);
        //when
        List<User>resList = userService.allUsers();
        //then
        assertThat(resList).isNotNull();
        assertThat(resList.get(0)).isEqualTo(user1);
        assertThat(resList.get(1)).isEqualTo(user2);
    }

    @Test
    public void allByGender(){
        //given
        Gender expected = Gender.FEMALE;
        //when
        userService.allByGender(expected);
        //then
        ArgumentCaptor<Gender>genderArgumentCaptor = ArgumentCaptor.forClass(Gender.class);
        verify(userRepository).findAllByGender(genderArgumentCaptor.capture());

        Gender captured = genderArgumentCaptor.getValue();
        assertThat(captured).isEqualTo(expected);
    }

    @Test
    public void byIdFound(){
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user = new User("Poseidon", dob, "URL",Gender.MALE,"auth0Id");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        //when
        User res = userService.byId(1L);
        //then
        assertThat(res).isNotNull();
        assertThat(res).isEqualTo(user);
    }

    @Test
    public void byIdNotFound(){
        //given
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        //when
        User res = userService.byId(1L);
        //then
        assertThat(res).isNull();
    }

}