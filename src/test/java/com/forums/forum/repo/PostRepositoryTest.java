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
    private Topic topic1 = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
    private Topic topic2 = new Topic("Art", new Timestamp(System.currentTimeMillis()), "URL");
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
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String expectedTitle = "What is your fav sport";
        Post post  = new Post(expectedTitle, "Mine is judo",timestamp,user1, topic1);
        postRepository.save(post);
        //when
        Post expected = postRepository.findByTitle(expectedTitle);
        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getTitle()).isEqualTo(expectedTitle);

    }

    @Test
    public void findAllByTitle() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String expectedTitle = "What is your fav sport";
        Post post1  = new Post(expectedTitle, "Mine is judo",timestamp,user1, topic1);
        Post post2  = new Post("hey", "there",timestamp,user2, topic2);
        postRepository.saveAll(List.of(post1,post2));
        //when
        List<Post> expected = postRepository.findAllByTitle(expectedTitle);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getTitle()).isEqualTo(expectedTitle);

    }

    @Test
    public void findAllByTitleContaining() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String seq = "topg";
        Post post1  = new Post("hey"+seq, "Mine is judo",timestamp,user1, topic1);
        Post post2  = new Post("hey", "there",timestamp,user2, topic2);
        postRepository.saveAll(List.of(post1,post2));
        //when
        List<Post> expected = postRepository.findAllByTitleContaining(seq);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getTitle().contains(seq)).isTrue();
    }

    @Test
    public void findAllByUser() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Post post1  = new Post("jj", "Mine is judo",timestamp,user1, topic1);
        Post post2  = new Post("hey", "there",timestamp,user2, topic2);
        postRepository.saveAll(List.of(post1,post2));
        //when
        List<Post> expected = postRepository.findAllByUser(user1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getUser()).isEqualTo(user1);
    }

    @Test
    public void findAllByTopic() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Post post1  = new Post("jj", "Mine is judo",timestamp,user1, topic1);
        Post post2  = new Post("hey", "there",timestamp,user2, topic2);
        postRepository.saveAll(List.of(post1,post2));
        //when
        List<Post> expected = postRepository.findAllByTopic(topic1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getTopic()).isEqualTo(topic1);

    }

    @Test
    public void findAllByTopicAndUser() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Post post1  = new Post("jj", "Mine is judo",timestamp,user1, topic1);
        Post post2  = new Post("hey", "there",timestamp,user2, topic2);
        Post post3  = new Post("hey", "there",timestamp,user2, topic1);
        postRepository.saveAll(List.of(post1,post2,post3));
        //when
        List<Post> expected = postRepository.findAllByTopicAndUser(topic1,user2);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getUser()).isEqualTo(user2);
        assertThat(expected.get(0).getTopic()).isEqualTo(topic1);

    }
}