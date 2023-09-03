package com.forums.forum.service;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.IllegalUserNameException;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.PostRepository;
import com.forums.forum.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private FollowService followService;

    @Mock
    private ModelMapper modelMapper;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository,postRepository,  modelMapper, followService);
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


    @Test
    public void updateUserShouldThrowWhenUsernameExists() {
        //given
        Long userId = 1L;
        String userName = "existing_username";
        LocalDate dob = LocalDate.of(2000, Month.JANUARY, 15);
        String profileUrl = "new_profile_url";
        Gender gender = Gender.FEMALE;

        User existingUser = new User(userName, LocalDate.of(1999, Month.APRIL, 7), "URL", Gender.MALE, "auth0Id");

        when(userRepository.findById(any())).thenReturn(Optional.of(existingUser));
        when(userRepository.existsByUsername(userName)).thenReturn(true);

        //then
        assertThatThrownBy(() -> userService.updateUser(userId, userName, dob, profileUrl, gender))
                .isInstanceOf(IllegalUserNameException.class);

    }

    @Test
    public void testGetUserProfile() throws ResourceNotFoundException
    {

    }

}