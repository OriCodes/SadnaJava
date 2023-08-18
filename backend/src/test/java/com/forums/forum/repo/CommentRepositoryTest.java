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
class CommentRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

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
        commentRepository.deleteAll();
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void findByCommentId() {
        //given
        Comment comment1 = new Comment("Text", user1, post1);
        Long correctId = 1L, incorrectId = 2L;
        commentRepository.save(comment1);
        //when
        Comment correct = commentRepository.findByCommentId(correctId);
        Comment incorrect = commentRepository.findByCommentId(incorrectId);
        //then
        assertThat(incorrect).isNull();
        assertThat(correct).isNotNull();
        assertThat(correct.getCommentId()).isEqualTo(correctId);

    }

    @Test
    public void findAllByPost() {
        //given
        Comment comment1 = new Comment("Text", user1, post1);
        Comment comment2 = new Comment("Text", user2, post2);
        commentRepository.saveAll(List.of(comment1, comment2));
        //when
        List<Comment> expected = commentRepository.findAllByPost(post1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getPost()).isEqualTo(post1);
    }

    @Test
    public void findAllByUser() {
        //given
        Comment comment1 = new Comment("Text", user1, post1);
        Comment comment2 = new Comment("Text", user2, post2);
        commentRepository.saveAll(List.of(comment1, comment2));
        //when
        List<Comment> expected = commentRepository.findAllByUser(user1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getUser()).isEqualTo(user1);
    }

    @Test
    public void countAllByPost() {
        //given
        Comment comment1 = new Comment("Text", user1, post1);
        Comment comment2 = new Comment("Text", user2, post2);
        commentRepository.saveAll(List.of(comment1, comment2));
        //when
        int expected = commentRepository.countAllByPost(post1);
        //then
        assertThat(expected).isEqualTo(1);
    }

    @Test
    public void countAllByUser() {
        //given
        Comment comment1 = new Comment("Text", user1, post1);
        Comment comment2 = new Comment("Text", user2, post2);
        commentRepository.saveAll(List.of(comment1, comment2));
        //when
        int expected = commentRepository.countAllByUser(user1);
        //then
        assertThat(expected).isEqualTo(1);
    }
}