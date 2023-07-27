package com.forums.forum.service;

import com.forums.forum.exception.TopicAlreadyExistException;
import com.forums.forum.model.Topic;
import com.forums.forum.repo.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@AllArgsConstructor
@Service
public class TopicService {
    private final TopicRepository topicRepository;
    public List<Topic> allTopics(){
        return topicRepository.findAll();
    }
    public Topic byId(Long id){
        return topicRepository.findById(id).orElse(null);
    }

    public boolean topicExistByTitle(String title){
        return topicRepository.existsByTopicName(title);
    }

    public Topic byTopicName(String topicName){
        return topicRepository.findByTopicName(topicName);
    }

    public List<Topic> allTopicsContains(String seq){
        return topicRepository.findAllByTopicNameContains(seq);
    }

    public Topic addTopic(String topicName, Timestamp timestamp, String thumbnailUrl) throws TopicAlreadyExistException
    {
        if(topicRepository.existsByTopicName(topicName))
        {
            throw new TopicAlreadyExistException();
        }
        Topic newTopic = new Topic(topicName,timestamp,thumbnailUrl);
        return topicRepository.save(newTopic);
    }
}
