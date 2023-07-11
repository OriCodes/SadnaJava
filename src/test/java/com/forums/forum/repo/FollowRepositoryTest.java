package com.forums.forum.repo;

import com.forums.forum.model.Follow;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FollowRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;
    private User user1 = new User("Poseidon", 19, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", 10, "URL", Gender.FEMALE, "Auth");
    private User user3 = new User("zeus", 14, "URL", Gender.MALE, "Auth");

    @BeforeEach
    public void initiateDb(){
        userRepository.saveAll(List.of(user1, user2));
    }
    @AfterEach
    public void tearDown(){
        followRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    public void countAllByFollower() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Follow follow1 = new Follow(user1,user2,timestamp);
        Follow follow2 = new Follow(user2,user1,timestamp);
        Follow follow3 = new Follow(user3,user1,timestamp);
        followRepository.saveAll(List.of(follow1,follow2,follow3));
        //then
        int expected = followRepository.countAllByFollower(user1);
        //then
        assertThat(expected).isEqualTo(1);
    }

    @Test
    public void countAllByFollowed() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Follow follow1 = new Follow(user1,user2,timestamp);
        Follow follow2 = new Follow(user2,user1,timestamp);
        Follow follow3 = new Follow(user3,user1,timestamp);
        followRepository.saveAll(List.of(follow1,follow2,follow3));
        //then
        int expected = followRepository.countAllByFollowed(user1);
        //then
        assertThat(expected).isEqualTo(2);
    }

    @Test
    public void existsByFollowerAndFollowed() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Follow follow1 = new Follow(user1,user2,timestamp);
        Follow follow2 = new Follow(user2,user1,timestamp);
        Follow follow3 = new Follow(user3,user1,timestamp);
        followRepository.saveAll(List.of(follow1,follow2,follow3));
        //then
        boolean expectedTrue = followRepository.existsByFollowerAndFollowed(user1,user2);
        boolean expectedFalse = followRepository.existsByFollowerAndFollowed(user1,user3);
        //then
        assertThat(expectedFalse).isFalse();
        assertThat(expectedTrue).isTrue();
    }

    @Test
    public void findAllByFollower() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Follow follow1 = new Follow(user1,user2,timestamp);
        Follow follow2 = new Follow(user2,user1,timestamp);
        Follow follow3 = new Follow(user3,user1,timestamp);
        followRepository.saveAll(List.of(follow1,follow2,follow3));
        //then
        List<Follow> expected = followRepository.findAllByFollower(user1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getFollower()).isEqualTo(user1);
    }

    @Test
    public void findAllByFollowed() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Follow follow1 = new Follow(user1,user2,timestamp);
        Follow follow2 = new Follow(user2,user1,timestamp);
        Follow follow3 = new Follow(user3,user1,timestamp);
        followRepository.saveAll(List.of(follow1,follow2,follow3));
        //then
        List<Follow> expected = followRepository.findAllByFollowed(user2);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getFollowed()).isEqualTo(user2);
    }
}