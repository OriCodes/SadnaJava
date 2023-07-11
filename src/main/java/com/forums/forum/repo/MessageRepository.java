package com.forums.forum.repo;

import com.forums.forum.model.Message;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    boolean existsBySenderAndReceiver(User sender,User receiver);
    List<Message>findAllBySenderAndReceiver(User sender, User receiver);
}
