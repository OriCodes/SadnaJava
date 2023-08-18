package com.forums.forum.repo;

import com.forums.forum.model.Follow;
import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class FollowRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FollowRepository followRepository;
    private final LocalDate dob = LocalDate.of(2003, Month.DECEMBER, 14);

    private User user1 = new User("Poseidon", dob, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", dob, "URL", Gender.FEMALE, "Auth");
    private User user3 = new User("zeus", dob, "URL", Gender.MALE, "Auth");

    @BeforeEach
    public void initiateDb() {
        userRepository.saveAll(List.of(user1, user2));
    }

    @AfterEach
    public void tearDown() {
        followRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void countAllByFollower() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        Follow follow3 = new Follow(user3, user1);
        followRepository.saveAll(List.of(follow1, follow2, follow3));
        //then
        int expected = followRepository.countAllByFollower(user1);
        //then
        assertThat(expected).isEqualTo(1);
    }

    @Test
    public void countAllByFollowed() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        Follow follow3 = new Follow(user3, user1);
        followRepository.saveAll(List.of(follow1, follow2, follow3));
        //then
        int expected = followRepository.countAllByFollowed(user1);
        //then
        assertThat(expected).isEqualTo(2);
    }

    @Test
    public void existsByFollowerAndFollowed() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        Follow follow3 = new Follow(user3, user1);
        followRepository.saveAll(List.of(follow1, follow2, follow3));
        //then
        boolean expectedTrue = followRepository.existsByFollowerAndFollowed(user1, user2);
        boolean expectedFalse = followRepository.existsByFollowerAndFollowed(user1, user3);
        //then
        assertThat(expectedFalse).isFalse();
        assertThat(expectedTrue).isTrue();
    }

    @Test
    public void findAllByFollower() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        Follow follow3 = new Follow(user3, user1);
        followRepository.saveAll(List.of(follow1, follow2, follow3));
        //then
        List<Follow> expected = followRepository.findAllByFollower(user1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getFollower()).isEqualTo(user1);
    }

    @Test
    public void findAllByFollowed() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        Follow follow3 = new Follow(user3, user1);
        followRepository.saveAll(List.of(follow1, follow2, follow3));
        //then
        List<Follow> expected = followRepository.findAllByFollowed(user2);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getFollowed()).isEqualTo(user2);
    }

    @Test
    public void findByFollowerAndFollowed() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        followRepository.saveAll(List.of(follow1, follow2));
        //when
        Follow res = followRepository.findByFollowerAndAndFollowed(user1, user2);
        //then
        assertThat(res).isNotNull();
        assertThat(res).isEqualTo(follow1);
    }

    @Test
    public void deleteAllByFollowerAndFollowed() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        followRepository.saveAll(List.of(follow1, follow2));
        //when
        followRepository.deleteAllByFollowerAndAndFollowed(user1, user2);
        //then
        List<Follow> expectedList = followRepository.findAll();
        assertThat(expectedList).isNotNull();
        assertThat(expectedList.size()).isEqualTo(1);
    }

    @Test
    public void deleteAllByFollower() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        followRepository.saveAll(List.of(follow1, follow2));
        //when
        followRepository.deleteAllByFollower(user1);
        //then
        List<Follow> expectedList = followRepository.findAll();
        assertThat(expectedList).isNotNull();
        assertThat(expectedList.size()).isEqualTo(1);
        assertThat(expectedList.get(0).getFollower()).isNotEqualTo(user1);
    }

    @Test
    public void deleteAllByFollowed() {
        //given
        Follow follow1 = new Follow(user1, user2);
        Follow follow2 = new Follow(user2, user1);
        followRepository.saveAll(List.of(follow1, follow2));
        //when
        followRepository.deleteAllByFollowed(user1);
        //then
        List<Follow> expectedList = followRepository.findAll();
        assertThat(expectedList).isNotNull();
        assertThat(expectedList.size()).isEqualTo(1);
        assertThat(expectedList.get(0).getFollowed()).isNotEqualTo(user1);
    }
}