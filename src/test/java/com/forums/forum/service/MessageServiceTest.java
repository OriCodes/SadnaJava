package com.forums.forum.service;

import com.forums.forum.model.Gender;
import com.forums.forum.model.Message;
import com.forums.forum.model.Post;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    private MessageService messageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        messageService = new MessageService(messageRepository);
    }
    @Test
    public void getConversation() {
        //given
        User user1 = new User("Yoav",19,"PhotoURL", Gender.MALE,"authOId");
        User user2 = new User("Yonatan",15,"PhotoURL", Gender.FEMALE,"authOId");

        Timestamp timestamp1 = Timestamp.valueOf("2023-07-26 12:34:56");
        Timestamp timestamp2 = Timestamp.valueOf("2023-07-26 13:45:12");


        List<Message> side1 = Arrays.asList(new Message(1,"Hey",timestamp1,user1,user2));

        List<Message> side2 = Arrays.asList(new Message(2,"Hello",timestamp2,user2,user1));
        //mocking
        when(messageRepository.findAllBySenderAndReceiver(user1, user2)).thenReturn(side1);
        when(messageRepository.findAllBySenderAndReceiver(user2, user1)).thenReturn(side2);

        List<Message> expectedConversation = new ArrayList<>();
        expectedConversation.addAll(side1);
        expectedConversation.addAll(side2);
        expectedConversation.sort(Comparator.comparing(Message::getCreatedTimeStamp));

        //when
        List<Message> conversation = messageService.getConversation(user1,user2);
        //then
        assertThat(conversation).isNotNull();
        assertThat(conversation.size()).isEqualTo(expectedConversation.size());
        assertThat(conversation.get(0)).isEqualTo(expectedConversation.get(0));
        assertThat(conversation.get(1)).isEqualTo(expectedConversation.get(1));


    }

    @Test
    public void addMessage() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String content = "Hey";
        User sender = new User();
        User receiver = new User();

        //when
        messageService.addMessage(sender,receiver,content,timestamp);
        //then
        ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class) ;
        verify(messageRepository).save(messageArgumentCaptor.capture());
        Message capturedMessage = messageArgumentCaptor.getValue();

        assertThat(capturedMessage.getReceiver()).isEqualTo(receiver);
        assertThat(capturedMessage.getSender()).isEqualTo(sender);
        assertThat(capturedMessage.getContent()).isEqualTo(content);
        assertThat(capturedMessage.getCreatedTimeStamp()).isEqualTo(timestamp);

    }

    @Test
    public void getAmountOfMessagesSent() {
        //given
        User user1 = new User("Yoav",19,"PhotoURL", Gender.MALE,"authOId");
        int expected = 5;
        when(messageRepository.countAllBySender(user1)).thenReturn(expected);
        //when
        int res = messageService.getAmountOfMessagesSent(user1);
        //then
        assertThat(res).isEqualTo(expected);
    }

    @Test
    public void hasConversationBetween() {
        User user1 = new User("Yoav",19,"PhotoURL", Gender.MALE,"authOId");
        User user2 = new User("Yonatan",15,"PhotoURL", Gender.FEMALE,"authOId");

        when(messageRepository.existsBySenderAndReceiver(user1,user2)).thenReturn(true);

        //when
        boolean res = messageService.hasConversationBetween(user1,user2);
        //then
        assertThat(res).isTrue();
    }
}