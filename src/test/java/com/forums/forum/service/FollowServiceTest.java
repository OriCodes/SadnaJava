package com.forums.forum.service;

import com.forums.forum.exception.AlreadyFollowsException;
import com.forums.forum.exception.UserNameAlreadyExistException;
import com.forums.forum.model.Follow;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.FollowRepository;
import com.forums.forum.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    @Mock
    FollowRepository followRepository;
    @Mock
    UserRepository userRepository;
    FollowService followService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        followService = new FollowService(followRepository,userRepository);
    }

    @Test
    public void isFollowing() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE,"auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        //when
        followService.isFollowing(1L,2L);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(followRepository).existsByFollowerAndFollowed(userArgumentCaptor.capture(),userArgumentCaptor.capture());

        List<User> capturedUsers = userArgumentCaptor.getAllValues();
        assertThat(capturedUsers.size()).isEqualTo(2);
        assertThat(user1).isEqualTo(capturedUsers.get(0));
        assertThat(user2).isEqualTo(capturedUsers.get(1));
    }

    @Test
    public void getNumberOfFollowers() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //when
        followService.getNumberOfFollowers(1L);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(followRepository).countAllByFollowed(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isNotNull();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void getNumberOfFollowed() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //when
        followService.getNumberOfFollowed(1L);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(followRepository).countAllByFollower(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isNotNull();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void byFollowAndFollower() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE,"auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        //when
        followService.byFollowAndFollower(1L, 2L);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(followRepository).findByFollowerAndAndFollowed(userArgumentCaptor.capture(),userArgumentCaptor.capture());

        List<User> capturedUsers = userArgumentCaptor.getAllValues();
        assertThat(capturedUsers.size()).isEqualTo(2);
        assertThat(user1).isEqualTo(capturedUsers.get(0));
        assertThat(user2).isEqualTo(capturedUsers.get(1));
    }

    @Test
    public void getFollowers() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        //when
        followService.getFollowers(1L);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(followRepository).findAllByFollowed(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isNotNull();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void getFollowed() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        //when
        followService.getFollowed(1L);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(followRepository).findAllByFollower(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isNotNull();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void addFollowSuccess() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        when(followRepository.existsByFollowerAndFollowed(any(), any())).thenReturn(false);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE, "auth0Id");
        User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE, "auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //when
        followService.addFollow(1L, 2L, timestamp);
        //then
        ArgumentCaptor<Follow> followArgumentCaptor = ArgumentCaptor.forClass(Follow.class);
        verify(followRepository).save(followArgumentCaptor.capture());

        Follow captured = followArgumentCaptor.getValue();
        assertThat(captured).isNotNull();
        assertThat(captured.getFollower()).isEqualTo(user1);
        assertThat(captured.getFollowed()).isEqualTo(user2);
        assertThat(captured.getFollowDate()).isEqualTo(timestamp);
    }

    @Test
    public void addFollowShouldThrowAlreadyExist() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        when(followRepository.existsByFollowerAndFollowed(any(),any())).thenReturn(true);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE,"auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //then
        assertThatThrownBy(()-> followService.addFollow(1L,2L,timestamp)).
                isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User with id " + 1L + " already follow User with id " + 2L);
        verify(followRepository, never()).save(any());
    }


    @Test
    public void addFollowWithSameUserShouldThrow() {
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user = new User("Poseidon", dob, "profileURL", Gender.MALE, "auth0Id");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        assertThatThrownBy(() -> followService.addFollow(1L, 1L, timestamp))
                .isInstanceOf(IllegalArgumentException.class).
                hasMessageContaining("Follower and followed cannot be the same user.");
    }


}