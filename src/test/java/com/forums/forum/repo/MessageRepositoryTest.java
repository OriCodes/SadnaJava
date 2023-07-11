package com.forums.forum.repo;

import com.forums.forum.model.Gender;
import com.forums.forum.model.Message;
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
class MessageRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    private User user1 = new User("Poseidon", 19, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", 10, "URL", Gender.FEMALE, "Auth");

    @BeforeEach
    public void initiateDb(){
        userRepository.saveAll(List.of(user1, user2));
    }
    @AfterEach
    public void tearDown(){
        messageRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    public void existsBySenderAndReceiver() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message = new Message("content",timestamp,user1,user2);
        messageRepository.save(message);
        //when
        boolean expectedTrue = messageRepository.existsBySenderAndReceiver(user1,user2);
        boolean expectedFalse = messageRepository.existsBySenderAndReceiver(user2,user1);
        //then
        assertThat(expectedFalse).isFalse();
        assertThat(expectedTrue).isTrue();
    }

    @Test
    public void countAllBySender() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message1 = new Message("content",timestamp,user1,user2);
        Message message2 = new Message("content",timestamp,user2,user1);
        messageRepository.saveAll(List.of(message1,message2));
        //when
        int expected = messageRepository.countAllBySender(user1);
        //then
        assertThat(expected).isEqualTo(1);

    }

    @Test
    public void findAllBySenderAndReceiver() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message1 = new Message("content",timestamp,user1,user2);
        Message message2 = new Message("content",timestamp,user2,user1);
        messageRepository.saveAll(List.of(message1,message2));
        //when
        List<Message> expected = messageRepository.findAllBySenderAndReceiver(user1,user2);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getSender()).isEqualTo(user1);
        assertThat(expected.get(0).getReceiver()).isEqualTo(user2);
    }
}