package com.forums.forum.repo;

import com.forums.forum.model.Gender;
import com.forums.forum.model.Post;
import com.forums.forum.model.Topic;
import com.forums.forum.model.User;
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
    private User user1 = new User("Poseidon", 19, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", 10, "URL", Gender.FEMALE, "Auth");
    private Topic topic1 = new Topic("Football", new Timestamp(System.currentTimeMillis()), "URL");
    private Topic topic2 = new Topic("Judo", new Timestamp(System.currentTimeMillis()), "URL");
    @BeforeEach
    public void initiateDb(){
        userRepository.saveAll(List.of(user1, user2));
        topicRepository.saveAll(List.of(topic1, topic2));
    }
    @AfterEach
    public void tearDown(){
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    public void existsByTitle() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String expectedTitle = "What is your fav sport";
        Post post  = new Post(expectedTitle, "Mine is judo",timestamp,user1, topic1);
        postRepository.save(post);
        //when
        boolean expectedTrue = postRepository.existsByTitle(expectedTitle);
        boolean expectedFalse = postRepository.existsByTitle(expectedTitle+"#");
        //then
        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();


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