package com.forums.forum.repo;

import com.forums.forum.model.Gender;
import com.forums.forum.model.Post;
import com.forums.forum.model.Topic;
import com.forums.forum.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
public class CascadeTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private TopicRepository topicRepository;

    @AfterEach
    public void tearDown(){
        postRepository.deleteAll();
        userRepository.deleteAll();
        topicRepository.deleteAll();
    }

    @Test
    public void testDeletePostAndUser(){
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        User user1 = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        User user2 = new User("Venus", dob1, "URL", Gender.FEMALE, "Auth");
        userRepository.saveAll(List.of(user1, user2));

        Timestamp timeStamp = new Timestamp(System.currentTimeMillis());
        String expectedTopic = "Basketball";
        Topic topic = new Topic(expectedTopic, timeStamp, "URL");
        topicRepository.save(topic);

        Post shouldStay = new Post("title","text", timeStamp,user1,topic);
        Post shouldBeRemoved = new Post("title","text", timeStamp,user2,topic);
        postRepository.saveAll(List.of(shouldStay,shouldBeRemoved));
        //when
        userRepository.deleteById(1L);
        //then
        List<User> users = userRepository.findAll();
        List<Post>posts = postRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0)).isEqualTo(user1);

        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0)).isEqualTo(shouldStay);


    }

}
