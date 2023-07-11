package com.forums.forum.repo;

import com.forums.forum.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class CommentRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private  PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private User user1 = new User("Poseidon", 19, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", 10, "URL", Gender.FEMALE, "Auth");
    private Topic topic = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
    private Post post1 = new Post("Post1", "txt", new Timestamp(System.currentTimeMillis()),user1, topic);
    private Post post2 = new Post("Post2", "txt", new Timestamp(System.currentTimeMillis()),user2, topic);

    @BeforeEach
    public void initiateDb(){
        userRepository.saveAll(List.of(user1, user2));
        topicRepository.save(topic);
        postRepository.saveAll(List.of(post1,post2));
    }
    @AfterEach
    public void tearDown(){
        commentRepository.deleteAll();
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    public void findAllByPost() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comment1 = new Comment("Text",timestamp,user1,post1);
        Comment comment2 = new Comment("Text",timestamp,user2,post2);
        commentRepository.saveAll(List.of(comment1,comment2));
        //when
        List<Comment>expected = commentRepository.findAllByPost(post1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getPost()).isEqualTo(post1);
    }

    @Test
    public void findAllByUser() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comment1 = new Comment("Text",timestamp,user1,post1);
        Comment comment2 = new Comment("Text",timestamp,user2,post2);
        commentRepository.saveAll(List.of(comment1,comment2));
        //when
        List<Comment>expected = commentRepository.findAllByUser(user1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getUser()).isEqualTo(user1);
    }

    @Test
    public void countAllByPost() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comment1 = new Comment("Text",timestamp,user1,post1);
        Comment comment2 = new Comment("Text",timestamp,user2,post2);
        commentRepository.saveAll(List.of(comment1,comment2));
        //when
        int expected = commentRepository.countAllByPost(post1);
        //then
        assertThat(expected).isEqualTo(1);
    }

    @Test
    public void countAllByUser() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Comment comment1 = new Comment("Text",timestamp,user1,post1);
        Comment comment2 = new Comment("Text",timestamp,user2,post2);
        commentRepository.saveAll(List.of(comment1,comment2));
        //when
        int expected = commentRepository.countAllByUser(user1);
        //then
        assertThat(expected).isEqualTo(1);
    }
}