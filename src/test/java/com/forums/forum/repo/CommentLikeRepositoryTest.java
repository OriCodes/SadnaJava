package com.forums.forum.repo;

import org.junit.jupiter.api.Test;

import com.forums.forum.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CommentLikeRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    private final LocalDate dob = LocalDate.of(2003, Month.DECEMBER,14);

    private User user1 = new User("Poseidon", dob, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", dob, "URL", Gender.FEMALE, "Auth");
    private Topic topic = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
    private Post post = new Post("Post1", "txt", new Timestamp(System.currentTimeMillis()),user1, topic);
    private Comment comment1 = new Comment("Text",new Timestamp(System.currentTimeMillis()),user1,post);
    private Comment comment2 = new Comment("Text",new Timestamp(System.currentTimeMillis()),user2,post);


    @BeforeEach
    public void initiateDb(){
        userRepository.saveAll(List.of(user1, user2));
        topicRepository.save(topic);
        postRepository.save(post);
        commentRepository.saveAll(List.of(comment1,comment2));
    }
    @AfterEach
    public void tearDown(){
        commentLikeRepository.deleteAll();
        commentRepository.deleteAll();
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void countAllByComment() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommentLike commentLike1 = new CommentLike(user1,comment1,timestamp);
        CommentLike commentLike2 = new CommentLike(user2,comment2,timestamp);
        commentLikeRepository.saveAll(List.of(commentLike1,commentLike2));
        //when
        int expected = commentLikeRepository.countAllByComment(comment1);
        //then
        assertThat(expected).isEqualTo(1);
    }

    @Test
    public void existsByCommentAndUser() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommentLike commentLike1 = new CommentLike(user1,comment1,timestamp);
        CommentLike commentLike2 = new CommentLike(user2,comment2,timestamp);
        commentLikeRepository.saveAll(List.of(commentLike1,commentLike2));
        //when
        boolean expectedTrue = commentLikeRepository.existsByCommentAndUser(comment1,user1);
        boolean expectedFalse = commentLikeRepository.existsByCommentAndUser(comment1,user2);
        //then
        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();
    }

    @Test
    public void deleteAllByCommentAndUser() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        CommentLike commentLike1 = new CommentLike(user1,comment1,timestamp);
        CommentLike commentLike2 = new CommentLike(user2,comment2,timestamp);
        commentLikeRepository.saveAll(List.of(commentLike1,commentLike2));
        //when
        commentLikeRepository.deleteAllByCommentAndUser(comment1, user1);
        List<CommentLike>expectedList = commentLikeRepository.findAll(); // the expected size should be 1
        //then
        assertThat(expectedList.size()).isEqualTo(1);

    }
}