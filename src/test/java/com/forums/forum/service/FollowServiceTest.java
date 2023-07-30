package com.forums.forum.service;

import com.forums.forum.exception.AlreadyFollowsException;
import com.forums.forum.exception.UserNameAlreadyExistException;
import com.forums.forum.model.Follow;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.FollowRepository;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {
    @Mock
    FollowRepository followRepository;
    FollowService followService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        followService = new FollowService(followRepository);
    }

    @Test
    public void isFollowing() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE,"auth0Id");
        //when
        followService.isFollowing(user1, user2);
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
        //when
        followService.getNumberOfFollowers(user);
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
        //when
        followService.getNumberOfFollowed(user);
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
        //when
        followService.byFollowAndFollower(user1, user2);
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
        //when
        followService.getFollowers(user);
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
        //when
        followService.getFollowed(user);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(followRepository).findAllByFollower(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isNotNull();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void addFollowSuccess() {
        try {
            //given
            LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
            when(followRepository.existsByFollowerAndFollowed(any(), any())).thenReturn(false);
            User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE, "auth0Id");
            User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE, "auth0Id");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            //when
            followService.addFollow(user1, user2, timestamp);
            //then
            ArgumentCaptor<Follow> followArgumentCaptor = ArgumentCaptor.forClass(Follow.class);
            verify(followRepository).save(followArgumentCaptor.capture());

            Follow captured = followArgumentCaptor.getValue();
            assertThat(captured).isNotNull();
            assertThat(captured.getFollower()).isEqualTo(user1);
            assertThat(captured.getFollowed()).isEqualTo(user2);
            assertThat(captured.getFollowDate()).isEqualTo(timestamp);

        }catch (AlreadyFollowsException e){
            System.out.println(e.getStackTrace());
        }
    }

    @Test
    public void addFollowShouldThrowAlreadyExist() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        when(followRepository.existsByFollowerAndFollowed(any(),any())).thenReturn(true);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE,"auth0Id");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //then
        assertThatThrownBy(()-> followService.addFollow(user1,user2,timestamp)).
                isInstanceOf(AlreadyFollowsException.class);
        verify(followRepository, never()).save(any());
    }
    @Test
    public void addFollowWithNullParametersShouldThrow() {
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE, "auth0Id");
        User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE, "auth0Id");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        assertThatThrownBy(() -> followService.addFollow(null, user2, timestamp))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> followService.addFollow(user1, null, timestamp))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> followService.addFollow(user1, user2, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void addFollowWithSameUserShouldThrow() {
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE, "auth0Id");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        assertThatThrownBy(() -> followService.addFollow(user1, user1, timestamp))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void unfollow(){
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
        User user1 = new User("Poseidon", dob, "profileURL", Gender.MALE,"auth0Id");
        User user2 = new User("Venus", dob, "profileURL", Gender.FEMALE,"auth0Id");
        //when
        followService.unfollow(user1, user2);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(followRepository).deleteAllByFollowerAndAndFollowed(
                userArgumentCaptor.capture(),
                userArgumentCaptor.capture())
        ;

        List<User> capturedUsers = userArgumentCaptor.getAllValues();
        assertThat(capturedUsers.size()).isEqualTo(2);
        assertThat(user1).isEqualTo(capturedUsers.get(0));
        assertThat(user2).isEqualTo(capturedUsers.get(1));
    }
}