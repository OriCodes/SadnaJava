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
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MessageRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    private final LocalDate dob = LocalDate.of(2003, Month.DECEMBER,14);
    private User user1 = new User("Poseidon", dob, "URL", Gender.MALE, "Auth");
    private User user2 = new User("Venus", dob, "URL", Gender.FEMALE, "Auth");

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
    public void findByMessageId() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message1 = new Message("content",timestamp,user1,user2);
        Long correctId = 1L;
        Long incorrectId = 2L;
        messageRepository.save(message1);
        //when
        Message correct = messageRepository.findByMessageId(correctId);
        Message incorrect = messageRepository.findByMessageId(incorrectId);
        //then
        assertThat(correct).isNotNull();
        assertThat(correct.getMessageId()).isEqualTo(correctId);
        assertThat(incorrect).isNull();

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

    @Test
    public void findAllByReceiver() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message1 = new Message("content",timestamp,user1,user2);
        Message message2 = new Message("content",timestamp,user2,user1);
        messageRepository.saveAll(List.of(message1,message2));
        //when
        List<Message> expected = messageRepository.findAllByReceiver(user1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getReceiver()).isEqualTo(user1);
    }

    @Test
    public void findAllBySender() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message1 = new Message("content",timestamp,user1,user2);
        Message message2 = new Message("content",timestamp,user2,user1);
        messageRepository.saveAll(List.of(message1,message2));
        //when
        List<Message> expected = messageRepository.findAllBySender(user1);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getSender()).isEqualTo(user1);
    }

    @Test
    public void deleteAllBySender() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message1 = new Message("content",timestamp,user1,user2);
        Message message2 = new Message("content",timestamp,user2,user1);
        messageRepository.saveAll(List.of(message1,message2));
        //when
        messageRepository.deleteAllBySender(user1);
        //then
        List<Message> expected = messageRepository.findAll();
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getSender()).isNotEqualTo(user1);
    }

    @Test
    public void deleteAllByReceiver() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Message message1 = new Message("content",timestamp,user1,user2);
        Message message2 = new Message("content",timestamp,user2,user1);
        messageRepository.saveAll(List.of(message1,message2));
        //when
        messageRepository.deleteAllByReceiver(user1);
        //then
        List<Message> expected = messageRepository.findAll();
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getReceiver()).isNotEqualTo(user1);
    }
}