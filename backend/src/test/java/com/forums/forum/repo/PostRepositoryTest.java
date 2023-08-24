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

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PostRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TopicRepository topicRepository;
    @Autowired
    private PostRepository postRepository;

    private final LocalDate dob = LocalDate.of(2003, Month.DECEMBER, 14);
    private User user1 = new User("Poseidon", dob, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", dob, "URL", Gender.FEMALE, "Auth");
    private Topic topic1 = new Topic("Sport", "URL");
    private Topic topic2 = new Topic("Art", "URL");

    @BeforeEach
    public void initiateDb() {
        userRepository.saveAll(List.of(user1, user2));
        topicRepository.saveAll(List.of(topic1, topic2));
    }

    @AfterEach
    public void tearDown() {
        postRepository.deleteAll();
        topicRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void existsByTitle() {
        //given
        String expectedTitle = "What is your fav sport";
        Post post = new Post(expectedTitle, "Mine is judo", user1, topic1);
        postRepository.save(post);
        //when
        boolean expectedTrue = postRepository.existsByTitle(expectedTitle);
        boolean expectedFalse = postRepository.existsByTitle(expectedTitle + "#");
        //then
        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();
    }

    @Test
    public void existsByTitleAndTopic() {
        //given
        String expectedTitle = "What is your fav sport";
        Post post = new Post(expectedTitle, "Mine is judo", user1, topic1);
        postRepository.save(post);
        //when
        boolean expectedTrue = postRepository.existsByTitleAndTopic(expectedTitle, topic1);
        boolean expectedFalseTitle = postRepository.existsByTitleAndTopic(expectedTitle + "#", topic1);
        boolean expectedFalseTopic = postRepository.existsByTitleAndTopic(expectedTitle, topic2);
        //then
        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalseTitle).isFalse();
        assertThat(expectedFalseTopic).isFalse();


    }

    @Test
    public void findByTitleName() {
        //given
        String expectedTitle = "What is your fav sport";
        Post post = new Post(expectedTitle, "Mine is judo", user1, topic1);
        postRepository.save(post);
        //when
        Post expected = postRepository.findByTitle(expectedTitle);
        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getTitle()).isEqualTo(expectedTitle);

    }


    @Test
    public void findByTitleAndTopic() {
        //given
        String expectedTitle = "What is your fav sport";
        Post post1 = new Post(expectedTitle, "Mine is judo", user1, topic1);
        Post post2 = new Post(expectedTitle, "Mine is judo", user1, topic2);
        postRepository.saveAll(List.of(post1, post2));
        //when
        Post expected = postRepository.findByTitleAndTopic(expectedTitle, topic1);
        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getTitle()).isEqualTo(expectedTitle);
        assertThat(expected.getTopic()).isEqualTo(topic1);

    }

    @Test
    public void findAllByTitle() {
        //given
        String expectedTitle = "What is your fav sport";
        Post post1 = new Post(expectedTitle, "Mine is judo", user1, topic1);
        Post post2 = new Post("hey", "there", user2, topic2);
        postRepository.saveAll(List.of(post1, post2));
        //when
        List<Post> expected = postRepository.findAllByTitle(expectedTitle);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getTitle()).isEqualTo(expectedTitle);

    }


    @Test
    public void findAllByTopicAndTitle() {
        //given
        String expectedTitle = "What is your fav sport";
        Post post1 = new Post(expectedTitle, "Mine is judo", user1, topic1);
        Post post2 = new Post("hey", "there", user2, topic2);
        Post post3 = new Post(expectedTitle, "Mine is judo", user1, topic2);
        Post post4 = new Post("**", "Mine is judo", user1, topic1);
        postRepository.saveAll(List.of(post1, post2, post3, post4));
        //when
        List<Post> expected = postRepository.findAllByTopicAndTitle(topic1, expectedTitle);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(expected.get(0).getTopic()).isEqualTo(topic1);

    }

    @Test
    public void findAllByTopicAndTitleContaining() {
        //given
        String seq = "topg";
        Post post1 = new Post("hey" + seq, "Mine is judo", user1, topic1);
        Post post2 = new Post("hey", "there", user2, topic2);
        Post post3 = new Post("hey" + seq, "Mine is judo", user1, topic2);
        Post post4 = new Post("hey", "there", user2, topic1);
        postRepository.saveAll(List.of(post1, post2, post3, post4));
        //when
        List<Post> expected = postRepository.findAllByTopicAndTitleContaining(topic1, seq);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getTitle().contains(seq)).isTrue();
        assertThat(expected.get(0).getTopic()).isEqualTo(topic1);
    }

    @Test
    public void findAllByTitleContaining() {
        //given
        String seq = "topg";
        Post post1 = new Post("hey" + seq, "Mine is judo", user1, topic1);
        Post post2 = new Post("hey", "there", user2, topic2);
        postRepository.saveAll(List.of(post1, post2));
        //when
        List<Post> expected = postRepository.findAllByTitleContaining(seq);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getTitle().contains(seq)).isTrue();
    }

    @Test
    public void findAllByUser() {
        //given
        Post post1 = new Post("jj", "Mine is judo", user1, topic1);
        Post post2 = new Post("hey", "there", user2, topic2);
        postRepository.saveAll(List.of(post1, post2));
        //when
        List<Post> expected = postRepository.findAllByUser(user1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getUser()).isEqualTo(user1);
    }

    @Test
    public void findAllByTopic() {
        //given
        Post post1 = new Post("jj", "Mine is judo", user1, topic1);
        Post post2 = new Post("hey", "there", user2, topic2);
        postRepository.saveAll(List.of(post1, post2));
        //when
        List<Post> expected = postRepository.findAllByTopic(topic1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getTopic()).isEqualTo(topic1);

    }

    @Test
    public void findAllByTopicAndUser() {
        //given
        Post post1 = new Post("jj", "Mine is judo", user1, topic1);
        Post post2 = new Post("hey", "there", user2, topic2);
        Post post3 = new Post("hey", "there", user2, topic1);
        postRepository.saveAll(List.of(post1, post2, post3));
        //when
        List<Post> expected = postRepository.findAllByTopicAndUser(topic1, user2);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getUser()).isEqualTo(user2);
        assertThat(expected.get(0).getTopic()).isEqualTo(topic1);
    }

    @Test
    public void deleteTest() {
        //given
        String seq = "topg";
        User user3 = new User("Zeus", dob, "URL", Gender.MALE, "Auth");
        Post post1 = new Post("hey" + seq, "Mine is judo", user1, topic1);
        Post post2 = new Post("hey", "there", user2, topic2);
        Post post3 = new Post("hey" + seq, "Mine is judo", user1, topic2);
        Post post4 = new Post("hey", "there", user3, topic1);
        postRepository.saveAll(List.of(post1, post2, post3, post4));
        userRepository.save(user3);
        Long id = userRepository.findByUserName("Zeus").get().getUserId();
        //when
        userRepository.deleteById(id);
        //then
        List<Post> expected = postRepository.findAll();
//        User user = userRepository.findByUserName("Zeus");
        assertThat(expected.size()).isEqualTo(3);
//        assertThat(user).isNotNull();
    }

    @Test
    public void testDeletePostAndUser() {
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);

        Post shouldStay = new Post("title", "text", user1, topic1);
        Post shouldBeRemoved = new Post("title", "text", user2, topic1);
        postRepository.saveAll(List.of(shouldStay, shouldBeRemoved));
        //when
        userRepository.deleteById(1L);
        //then
        List<User> users = userRepository.findAll();
        List<Post> posts = postRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0)).isEqualTo(user2);

        assertThat(posts).isNotNull();
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0)).isEqualTo(shouldStay);


    }
}