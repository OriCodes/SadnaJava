package com.forums.forum.repo;

import com.forums.forum.model.Topic;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {
    boolean existsByTopicName(String topicName);

    Topic findByTopicName(String topicName);
    List<Topic> findAllByCreatedTimeStampBetween(Timestamp lowerBound, Timestamp upperBound);
    List<Topic> findAllByTopicNameContains(String seq);

}
