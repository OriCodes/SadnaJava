package com.forums.forum.repo;

import com.forums.forum.model.*;
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
class PostLikeRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostLikeRepository postLikeRepository;
    private final LocalDate dob = LocalDate.of(2003, Month.DECEMBER, 14);
    private User user1 = new User("Poseidon", dob, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", dob, "URL", Gender.FEMALE, "Auth");
    private Topic topic = new Topic("Sport", "URL");
    private Post post1 = new Post("Post1", "txt", user1, topic);
    private Post post2 = new Post("Post2", "txt", user2, topic);

    @BeforeEach
    public void initiateDb() {
        userRepository.saveAll(List.of(user1, user2));
        topicRepository.save(topic);
        postRepository.saveAll(List.of(post1, post2));
    }

    @AfterEach
    public void tearDown() {
        postLikeRepository.deleteAll();
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void countAllByPost() {
        //given
        PostLike postLike1 = new PostLike(user1, post1);
        PostLike postLike2 = new PostLike(user2, post2);
        postLikeRepository.saveAll(List.of(postLike1, postLike2));
        //when
        int expected = postLikeRepository.countAllByPost(post1);
        //then
        assertThat(expected).isEqualTo(1);
    }

    @Test
    public void existsByPostAndUser() {
        //given
        PostLike postLike1 = new PostLike(user1, post1);
        PostLike postLike2 = new PostLike(user2, post2);
        postLikeRepository.saveAll(List.of(postLike1, postLike2));
        //when
        boolean expectedTrue = postLikeRepository.existsByPostAndUser(post1, user1);
        boolean expectedFalse = postLikeRepository.existsByPostAndUser(post2, user1);
        //then
        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();
    }

    @Test
    public void deleteAllByPostAndUser() {
        //given
        PostLike postLike1 = new PostLike(user1, post1);
        PostLike postLike2 = new PostLike(user2, post2);
        postLikeRepository.saveAll(List.of(postLike1, postLike2));
        //when
        postLikeRepository.deleteAllByPostAndUser(post1, user1);
        List<PostLike> expectedList = postLikeRepository.findAll();
        //then
        assertThat(expectedList.size()).isEqualTo(1);
    }

    @Test
    public void deleteAllByPost() {
        //given
        PostLike postLike1 = new PostLike(user1, post1);
        PostLike postLike2 = new PostLike(user2, post2);
        postLikeRepository.saveAll(List.of(postLike1, postLike2));
        //when
        postLikeRepository.deleteAllByPost(post1);
        List<PostLike> expectedList = postLikeRepository.findAll();
        //then
        assertThat(expectedList.size()).isEqualTo(1);
        assertThat(expectedList.get(0).getPost()).isNotEqualTo(post1);
    }

    @Test
    public void deleteAllByUser() {
        //given
        PostLike postLike1 = new PostLike(user1, post1);
        PostLike postLike2 = new PostLike(user2, post2);
        postLikeRepository.saveAll(List.of(postLike1, postLike2));
        //when
        postLikeRepository.deleteAllByUser(user1);
        List<PostLike> expectedList = postLikeRepository.findAll();
        //then
        assertThat(expectedList.size()).isEqualTo(1);
        assertThat(expectedList.get(0).getUser()).isNotEqualTo(user1);
    }


    @Test
    public void findByPostLikeId() {
        //given
        PostLike postLike1 = new PostLike(user1, post1);
        Long correctId = 1L;
        Long incorrectId = 2L;
        postLikeRepository.save(postLike1);
        //when
        PostLike correct = postLikeRepository.findByPostLikeId(correctId);
        PostLike incorrect = postLikeRepository.findByPostLikeId(incorrectId);
        //then
        assertThat(incorrect).isNull();
        assertThat(correct).isNotNull();
        assertThat(correct.getPostLikeId()).isEqualTo(correctId);

    }
}