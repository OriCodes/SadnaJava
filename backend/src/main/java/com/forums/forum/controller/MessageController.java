package com.forums.forum.controller;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.model.Message;
import com.forums.forum.model.User;
import com.forums.forum.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(path = "api/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping(path = "/sendMessage")
    public @ResponseBody Message sendMessage(Long senderId, Long receiverId, String content) throws ResourceNotFoundException{
        return messageService.addMessage(senderId,receiverId,content);
    }

    @GetMapping(path = "/getConversation")
    public @ResponseBody List<Message>getConversation(Long user1Id, Long user2Id) throws ResourceNotFoundException{
        return messageService.getConversation(user1Id, user2Id);
    }

    @GetMapping(path = "/hasConversationBetween")
    public @ResponseBody boolean hasConversationBetween(Long user1Id, Long user2Id) throws ResourceNotFoundException{
        return messageService.hasConversationBetween(user1Id, user2Id);
    }

    @GetMapping(path = "/byId/{messageId}")
    public @ResponseBody Message byId(@PathVariable("messageId")Long messageId){
        return messageService.getById(messageId);
    }

    @GetMapping(path = "/messageSentCounter")
    public @ResponseBody int messageSentCounter(Long userId) throws ResourceNotFoundException{
        return messageService.getAmountOfMessagesSent(userId);
    }

    @GetMapping(path ="/newMessages")
    public @ResponseBody List<Message> newMessages(
            @RequestParam Long userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Timestamp timestamp
    ) throws ResourceNotFoundException{
        return messageService.newMessages(userId, timestamp);
    }

    @GetMapping("/usersHavingConversations")
    public @ResponseBody List<User> getUsersHavingConversations(Long userId) throws ResourceNotFoundException {
        return messageService.getUsersInMessages(userId);
    }
}
