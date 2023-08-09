package com.forums.forum.service;

import com.forums.forum.model.Gender;
import com.forums.forum.model.Message;
import com.forums.forum.model.User;
import com.forums.forum.repo.MessageRepository;
import com.forums.forum.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(messageRepository, userRepository);
    }

    @Test
    public void getConversation() {
        try { //given
            LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
            User user1 = new User("Yoav", dob, "PhotoURL", Gender.MALE, "authOId");
            User user2 = new User("Yonatan", dob, "PhotoURL", Gender.FEMALE, "authOId");
            Long id1 = 1L, id2 = 2L;
            Timestamp timestamp1 = Timestamp.valueOf("2023-07-26 12:34:56");
            Timestamp timestamp2 = Timestamp.valueOf("2023-07-26 13:45:12");


            List<Message> side1 = Arrays.asList(new Message(1, "Hey", timestamp1, user1, user2));

            List<Message> side2 = Arrays.asList(new Message(2, "Hello", timestamp2, user2, user1));
            //mocking
            when(userRepository.findById(id1)).thenReturn(Optional.of(user1));
            when(userRepository.findById(id2)).thenReturn(Optional.of(user2));
            when(messageRepository.findAllBySenderAndReceiver(user1, user2)).thenReturn(side1);
            when(messageRepository.findAllBySenderAndReceiver(user2, user1)).thenReturn(side2);

            List<Message> expectedConversation = new ArrayList<>();
            expectedConversation.addAll(side1);
            expectedConversation.addAll(side2);
            expectedConversation.sort(Comparator.comparing(Message::getCreatedTimeStamp));

            //when
            List<Message> conversation = messageService.getConversation(id1, id2);
            //then
            assertThat(conversation).isNotNull();
            assertThat(conversation.size()).isEqualTo(expectedConversation.size());
            assertThat(conversation.get(0)).isEqualTo(expectedConversation.get(0));
            assertThat(conversation.get(1)).isEqualTo(expectedConversation.get(1));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void addMessage() {
        try{        //given
            String content = "Hey";
            LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
            User receiver = new User("Yoav", dob, "PhotoURL", Gender.MALE, "authOId");
            User sender = new User("Yonatan", dob, "PhotoURL", Gender.FEMALE, "authOId");
            Long id1 = 1L, id2 = 2L;
            when(userRepository.findById(id1)).thenReturn(Optional.of(sender));
            when(userRepository.findById(id2)).thenReturn(Optional.of(receiver));

            //when
            messageService.addMessage(id1, id2, content);
            //then
            ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);
            verify(messageRepository).save(messageArgumentCaptor.capture());
            Message capturedMessage = messageArgumentCaptor.getValue();

            assertThat(capturedMessage.getReceiver()).isEqualTo(receiver);
            assertThat(capturedMessage.getSender()).isEqualTo(sender);
            assertThat(capturedMessage.getContent()).isEqualTo(content);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getAmountOfMessagesSent() {
        try{        //given
            LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
            User user = new User("Yoav", dob, "PhotoURL", Gender.MALE, "authOId");
            Long id = 1L;
            int expected = 5;
            when(userRepository.findById(id)).thenReturn(Optional.of(user));
            when(messageRepository.countAllBySender(user)).thenReturn(expected);
            //when
            int res = messageService.getAmountOfMessagesSent(id);
            //then
            assertThat(res).isEqualTo(expected);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void hasConversationBetween() {
        try {
            Long user1Id = 1L;
            Long user2Id = 2L;
            LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
            User user1 = new User("Yoav", dob, "PhotoURL", Gender.MALE, "authOId");
            User user2 = new User("Yonatan", dob, "PhotoURL", Gender.FEMALE, "authOId");

            when(userRepository.findById(user1Id)).thenReturn(Optional.of(user1));
            when(userRepository.findById(user2Id)).thenReturn(Optional.of(user2));
            when(messageRepository.existsBySenderAndReceiver(user1, user2)).thenReturn(true);

            //when
            boolean res = messageService.hasConversationBetween(user1Id, user2Id);

            //then
            assertThat(res).isTrue();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void newMessages() {
        try {
            Long userId = 1L;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            User user = new User(
                    "Yoav",
                    LocalDate.of(1999, Month.APRIL, 7),
                    "PhotoURL",
                    Gender.MALE,
                    "authOId"
            );
            when(userRepository.findById(userId)).thenReturn(Optional.of(user));

            //when
            List<Message> messages = messageService.newMessages(userId, timestamp);
            //then
            ArgumentCaptor<User> userArgCaptor = ArgumentCaptor.forClass(User.class);
            ArgumentCaptor<Timestamp> timestampArgCaptor = ArgumentCaptor.forClass(Timestamp.class);
            verify(messageRepository).findAllByReceiverAndCreatedTimeStampGreaterThanEqual(
                    userArgCaptor.capture(),
                    timestampArgCaptor.capture()
            );
            User capturedUser = userArgCaptor.getValue();
            Timestamp capturedTime = timestampArgCaptor.getValue();
            assertThat(capturedUser).isNotNull();
            assertThat(capturedTime).isNotNull();
            assertThat(capturedUser).isEqualTo(user);
            assertThat(capturedTime).isEqualTo(timestamp);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void newMessages_NoMessages() {
        try {
            Long userId = 1L;
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            User user = new User("Yoav", LocalDate.of(1999, Month.APRIL, 7), "PhotoURL", Gender.MALE, "authOId");

            when(userRepository.findById(userId)).thenReturn(Optional.of(user));
            when(messageRepository.findAllByReceiverAndCreatedTimeStampGreaterThanEqual(user, timestamp))
                    .thenReturn(new ArrayList<>());

            //when
            List<Message> messages = messageService.newMessages(userId, timestamp);

            //then
            assertThat(messages).isNull();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void getById() {
        Long messageId = 1L;
        Message message = new Message("content", new User(), new User());
        when(messageRepository.findById(messageId)).thenReturn(Optional.of(message));

        //when
        Message res = messageService.getById(messageId);

        //then
        assertThat(res).isEqualTo(message);
    }
}