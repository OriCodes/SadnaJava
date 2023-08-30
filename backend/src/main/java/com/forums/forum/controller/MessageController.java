package com.forums.forum.controller;

import com.forums.forum.dto.NewMessageBody;
import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.model.Message;
import com.forums.forum.model.User;
import com.forums.forum.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(path = "api/messages")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping(path = "/sendMessage")
    public @ResponseBody Message sendMessage(@AuthenticationPrincipal User sender, Long receiverId, @RequestBody NewMessageBody messageBody) throws ResourceNotFoundException {
        Long senderId = sender.getUserId();
        return messageService.addMessage(senderId, receiverId, messageBody.getMessage());
    }

    @GetMapping(path = "/getConversation")
    public @ResponseBody List<Message> getConversation(@AuthenticationPrincipal User currentUser, Long userId) throws ResourceNotFoundException {
        Long user1Id = currentUser.getUserId();
        return messageService.getConversation(user1Id, userId);
    }

    @GetMapping(path = "/hasConversationBetween")
    public @ResponseBody boolean hasConversationBetween(@AuthenticationPrincipal User currentUser, Long userId) throws ResourceNotFoundException {
        Long user1Id = currentUser.getUserId();
        return messageService.hasConversationBetween(user1Id, userId);
    }

    @GetMapping(path = "/byId/{messageId}")
    public @ResponseBody Message byId(@PathVariable("messageId") Long messageId) {
        return messageService.getById(messageId);
    }

    @GetMapping(path = "/messageSentCounter")
    public @ResponseBody int messageSentCounter(Long userId) throws ResourceNotFoundException {
        return messageService.getAmountOfMessagesSent(userId);
    }

    @GetMapping(path = "/newMessages")
    public @ResponseBody List<Message> newMessages(
            @AuthenticationPrincipal User user, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Timestamp timestamp
    ) throws ResourceNotFoundException {
        Long userId = user.getUserId();
        return messageService.newMessages(userId, timestamp);
    }

    @GetMapping("/usersHavingConversations")
    public @ResponseBody List<User> getUsersHavingConversations(@AuthenticationPrincipal User user) throws ResourceNotFoundException {
        Long userId = user.getUserId();
        return messageService.getUsersInMessages(userId);
    }
}
