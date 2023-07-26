package com.forums.forum.service;

import com.forums.forum.model.Message;
import com.forums.forum.model.User;
import com.forums.forum.repo.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class MessageService{
    private final MessageRepository messageRepository;

    public List<Message> getConversation(User user1, User user2){

        List<Message> side1 = messageRepository.findAllBySenderAndReceiver(user1, user2);
        List<Message> side2 = messageRepository.findAllBySenderAndReceiver(user2, user1);

        List<Message> conversation = new ArrayList<>();
        conversation.addAll(side2);
        conversation.addAll(side2);
        conversation.sort(Comparator.comparing(Message::getCreatedTimeStamp));

        return conversation;
    }

    public Message addMessage(User sender, User receiver, String content, Timestamp timestamp){
        Message message = new Message(content,timestamp,sender,receiver);
        return messageRepository.save(message);
    }
    public int getAmountOfMessagesSent(User user){
        return messageRepository.countAllBySender(user);
    }

    public boolean hasConversationBetween(User user1, User user2){
        return messageRepository.existsBySenderAndReceiver(user1, user2)||messageRepository.existsBySenderAndReceiver(user2, user1);
    }

}
