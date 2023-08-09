package com.forums.forum.service;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.model.Message;
import com.forums.forum.model.User;
import com.forums.forum.repo.MessageRepository;
import com.forums.forum.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public List<Message> getConversation(Long user1Id, Long user2Id) throws ResourceNotFoundException {

        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + user1Id + " not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + user2Id + " not found"));

        List<Message> side1 = messageRepository.findAllBySenderAndReceiver(user1, user2);
        List<Message> side2 = messageRepository.findAllBySenderAndReceiver(user2, user1);

        List<Message> conversation = new ArrayList<>();
        conversation.addAll(side1);
        conversation.addAll(side2);
        conversation.sort(Comparator.comparing(Message::getCreatedTimeStamp));

        return conversation;
    }

    public Message addMessage(Long senderId, Long receiverId, String content) throws ResourceNotFoundException {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + senderId + " not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + receiverId + " not found"));

        Message message = new Message(content, sender, receiver);
        return messageRepository.save(message);
    }

    public int getAmountOfMessagesSent(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        return messageRepository.countAllBySender(user);
    }

    public boolean hasConversationBetween(Long user1Id, Long user2Id) throws ResourceNotFoundException {
        User user1 = userRepository.findById(user1Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + user1Id + " not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + user2Id + " not found"));

        return messageRepository.existsBySenderAndReceiver(user1, user2) ||
                messageRepository.existsBySenderAndReceiver(user2, user1);
    }

    public Message getById(Long messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public List<Message> newMessages(Long userId, Timestamp timestamp) throws ResourceNotFoundException{
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        List<Message> result = messageRepository.findAllByReceiverAndCreatedTimeStampGreaterThanEqual(user, timestamp);

        return result;
    }

}
