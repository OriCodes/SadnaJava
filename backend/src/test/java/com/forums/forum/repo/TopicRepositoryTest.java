package com.forums.forum.repo;


import com.forums.forum.model.Topic;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TopicRepositoryTest {
    @Autowired
    private TopicRepository topicRepository;

    @AfterEach
    public void tearDown() {
        topicRepository.deleteAll();
    }

    @Test
    public void existsByTopicName() {
        //given
        String expectedTopic = "Basketball";
        Topic topic = new Topic(expectedTopic, "URL");
        topicRepository.save(topic);
        //when
        boolean expectedTrue = topicRepository.existsByTopicName(expectedTopic);
        boolean expectedFalse = topicRepository.existsByTopicName(expectedTopic + "#");
        //then
        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();
    }

    @Test
    public void findByTopicName() {
        //given
        String expectedTopic = "Basketball";
        Topic topic = new Topic(expectedTopic, "URL");
        topicRepository.save(topic);
        //when
        Topic foundTopic = topicRepository.findByTopicName(expectedTopic);
        //then
        assertThat(foundTopic).isNotNull();
        assertThat(foundTopic.getTopicName()).isEqualTo(expectedTopic);
    }


    @Test
    public void findAllByCreatedTimeStampBetween() {
        //given
        Timestamp lowerBound = Timestamp.valueOf("2023-07-09 10:00:00.123456789");
        Timestamp upperBound = Timestamp.valueOf("2023-07-09 11:30:00.987654321");
        Timestamp outOfBounds = Timestamp.valueOf("2023-07-10 09:45:00.555555555");
        Timestamp inBounds = new Timestamp((lowerBound.getTime() + upperBound.getTime()) / 2);
        Topic expected = new Topic("baseball", "URL");
        expected.setCreatedTimeStamp(inBounds);
        Topic notExpected = new Topic("baseball", "URL");
        notExpected.setCreatedTimeStamp(outOfBounds);
        topicRepository.saveAll(List.of(expected, notExpected));
        //when
        List<Topic> expectedList = topicRepository.findAllByCreatedTimeStampBetween(lowerBound, upperBound);
        //then
        assertThat(expectedList.size()).isEqualTo(1);
        assertThat(expectedList.get(0).getCreatedTimeStamp()).isEqualTo(inBounds);


    }

    @Test
    public void findAllByTopicNameContains() {
        //given
        Topic topic1 = new Topic("Football", "URL");
        Topic topic2 = new Topic("basketball", "URL");
        topicRepository.saveAll(List.of(topic1, topic2));
        String seq = "Fo";
        //when
        List<Topic> expectedList = topicRepository.findAllByTopicNameContains(seq);
        //then
        assertThat(expectedList.size()).isEqualTo(1);
        assertThat(expectedList.get(0).getTopicName().contains(seq)).isTrue();
    }
}