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
    void deleteAllBySender(User sender);
    void deleteAllByReceiver(User receiver);
    Message findByMessageId(Long id);
    List<Message>findAllBySenderAndReceiver(User sender, User receiver);
    List<Message>findAllByReceiver(User receiver);
    List<Message>findAllBySender(User sender);

}
