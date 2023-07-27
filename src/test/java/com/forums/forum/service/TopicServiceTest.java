package com.forums.forum.service;

import com.forums.forum.exception.TopicAlreadyExistException;
import com.forums.forum.model.Post;
import com.forums.forum.model.Topic;
import com.forums.forum.repo.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TopicServiceTest {
    @Mock
    private TopicRepository topicRepository;
    private TopicService topicService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        topicService = new TopicService(topicRepository);
    }

    @Test
    public void allTopics() {
        // given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Topic topic1 = new Topic( "Topic 1", timestamp,"Content 1");
        Topic topic2 = new Topic( "Topic 2", timestamp,"Content 2");
        List<Topic> topics = Arrays.asList(topic1, topic2);

        when(topicRepository.findAll()).thenReturn(topics);

        // when
        List<Topic> result = topicService.allTopics();

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0)).isEqualTo(topic1);
        assertThat(result.get(1)).isEqualTo(topic2);
    }

    @Test
    public void byIdTopicFound() {
        //given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Post> posts = new ArrayList<>();
        Long topicId = 1L;
        Topic topic = new Topic(topicId,"Baseball",timestamp,"URL",posts);

        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        //when
        Topic res = topicService.byId(topicId);

        //then
        assertThat(res).isNotNull();
        assertThat(res).isEqualTo(topic);
    }

    @Test
    public void byIdTopicNotFound() {
        //given
        Long topicId = 1L;
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        Topic res = topicService.byId(topicId);

        //then
        assertThat(res).isNull();

    }

    @Test
    public void topicExistByTitle() {
        //given
        String expected = "Hello";
        //when
        topicService.topicExistByTitle(expected);
        //then
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(topicRepository).existsByTopicName(stringArgumentCaptor.capture());
        assertThat(expected).isEqualTo(stringArgumentCaptor.getValue());
    }

    @Test
    public void byTopicName() {
        //given
        String expected = "TopicName";
        //when
        topicService.byTopicName(expected);
        //then
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(topicRepository).findByTopicName(stringArgumentCaptor.capture());
        assertThat(expected).isEqualTo(stringArgumentCaptor.getValue());
    }

    @Test
    public void allTopicsContains() {
        //given
        String expected = "Hello";
        //when
        topicService.allTopicsContains(expected);
        //then
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(topicRepository).findAllByTopicNameContains(stringArgumentCaptor.capture());
        assertThat(expected).isEqualTo(stringArgumentCaptor.getValue());
    }

    @Test
    public void addTopicSuccess() {
        try {
            //given
            when(topicRepository.existsByTopicName(anyString())).thenReturn(false);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String topicName = "topicName";
            String thumbNailUrl = "URL";
            Topic topic = new Topic(topicName,timestamp,thumbNailUrl);
            //when
            topicService.addTopic(topicName,timestamp,thumbNailUrl);
            //then
            ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
            verify(topicRepository).save(topicArgumentCaptor.capture());

            assertThat(topicArgumentCaptor.getValue()).isEqualTo(topic);
        }catch (TopicAlreadyExistException e){
            System.out.println(e.getStackTrace());
        }
    }

    @Test
    public void addTopicShouldThrow() {
        when(topicRepository.existsByTopicName(anyString())).thenReturn(true);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String topicName = "topicName";
        String thumbNailUrl = "URL";
        assertThatThrownBy(()->topicService.addTopic(topicName,timestamp,thumbNailUrl)).
                isInstanceOf(TopicAlreadyExistException.class);
        verify(topicRepository, never()).save(any());
    }
}