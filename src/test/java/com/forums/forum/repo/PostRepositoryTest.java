package com.forums.forum.repo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private  PostRepository postRepository;
    @BeforeEach
    public void initiateDb(){

    }
    @AfterEach
    public void tearDown(){

    }
    @Test
    public void existsByTitle() {
    }

    @Test
    public void findByTitleName() {
    }

    @Test
    public void findAllByTitle() {
    }

    @Test
    public void findAllByTitleContaining() {
    }

    @Test
    public void findAllByUser() {
    }

    @Test
    public void findAllByTopic() {
    }

    @Test
    public void findAllByTopicAndUser() {
    }
}