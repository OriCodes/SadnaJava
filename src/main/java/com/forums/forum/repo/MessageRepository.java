package com.forums.forum.repo;

import com.forums.forum.model.Message;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {
    boolean existsBySenderAndReceiver(User sender,User receiver);
    int countAllBySender(User sender);
    List<Message>findAllBySenderAndReceiver(User sender, User receiver);
}
