package com.forums.forum.service;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserActionNotAllowedException;
import com.forums.forum.model.*;
import com.forums.forum.repo.PostLikeRepository;
import com.forums.forum.repo.PostRepository;
import com.forums.forum.repo.TopicRepository;
import com.forums.forum.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private PostLikeRepository postLikeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TopicRepository topicRepository;
    private PostService postService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postService = new PostService(postRepository, postLikeRepository, userRepository, topicRepository);
    }

    @Test
    public void byId() {
        //given
        Post post = new Post();
        Long validId = 1L, notValidId = 2L;
        post.setPostId(validId);
        when(postRepository.findById(validId)).thenReturn(Optional.of(post));
        when(postRepository.findById(notValidId)).thenReturn(Optional.empty());
        //when
        Post valid = postService.byId(validId);
        Post notValid = postService.byId(notValidId);
        //then
        assertThat(valid).isNotNull();
        assertThat(valid.getPostId()).isEqualTo(validId);
        assertThat(notValid).isNull();
    }


    @Test
    public void isExistByTitle() {
        //given
        String title = "title";
        //when
        postService.isExistByTitle(title);
        //then
        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(postRepository).existsByTitle(stringArgumentCaptor.capture());
        String capturedTitle = stringArgumentCaptor.getValue();
        assertThat(capturedTitle).isEqualTo(title);
    }

    @Test
    public void isExistByTitleAndTopic() {
        //given
        try {
            String title = "title";
            Topic topic = new Topic("Topic", "URL");
            when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
            //when
            postService.isExistByTitleAndTopic(title, 1L);
            //then
            ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
            verify(postRepository).existsByTitleAndTopic(stringArgumentCaptor.capture(), topicArgumentCaptor.capture());
            String capturedTitle = stringArgumentCaptor.getValue();
            Topic capturedTopic = topicArgumentCaptor.getValue();
            assertThat(capturedTitle).isNotNull();
            assertThat(capturedTitle).isEqualTo(title);
            assertThat(capturedTopic).isNotNull();
            assertThat(capturedTopic).isEqualTo(topic);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void allPosts() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        User user1 = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");
        User user2 = new User("Venus", dob, "URL", Gender.FEMALE, "auth0Id");
        Topic topic = new Topic("Topic", "URL");
        Post post1 = new Post("title", "text", user1, topic);
        Post post2 = new Post("title", "text", user2, topic);
        List<Post> expecList = Arrays.asList(post1, post2);
        when(postRepository.findAll()).thenReturn(expecList);
        //then
        List<Post> resList = postService.allPosts();
        //then
        assertThat(resList).isNotNull();
        assertThat(resList.get(0)).isEqualTo(post1);
        assertThat(resList.get(1)).isEqualTo(post2);
    }


    @Test
    public void allByUser() {
        try {        //given
            LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
            User user = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            //when
            postService.allByUser(1L);
            //then
            ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
            verify(postRepository).findAllByUser(userArgumentCaptor.capture());
            User capturedUser = userArgumentCaptor.getValue();

            assertThat(capturedUser).isNotNull();
            assertThat(capturedUser).isEqualTo(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void allByTopic() {
        try {        //given
            Topic topic = new Topic("Topic", "URL");
            when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
            //when
            postService.allByTopic(1L);
            //then
            ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
            verify(postRepository).findAllByTopic(topicArgumentCaptor.capture());
            Topic capturedTopic = topicArgumentCaptor.getValue();

            assertThat(capturedTopic).isNotNull();
            assertThat(capturedTopic).isEqualTo(topic);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addPostSuccess() {
        try {        //given
            LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
            Topic topic = new Topic("Topic", "URL");
            User user = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");
            String title = "title", text = "text";
            when(topicRepository.findById(anyLong())).thenReturn(Optional.of(topic));
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            //when
            postService.addPost(1L, 1L, title, text);
            //then
            ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
            verify(postRepository).save(postArgumentCaptor.capture());

            Post capturedPost = postArgumentCaptor.getValue();
            assertThat(capturedPost).isNotNull();
            assertThat(capturedPost.getTitle()).isEqualTo(title);
            assertThat(capturedPost.getText()).isEqualTo(text);
            assertThat(capturedPost.getUser()).isEqualTo(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void addPostShouldThrowUserNotFound() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        String title = "title", text = "text";

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when & then
        assertThatThrownBy(() -> postService.addPost(1L, 1L, title, text)).
                isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User with id " + 1L + " not found");

        verify(postRepository, never()).save(any());

    }

    @Test
    public void addPostShouldThrowTopicNotFound() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        User user = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");
        String title = "title", text = "text";

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        //when & then
        assertThatThrownBy(() -> postService.addPost(1L, 1L, title, text)).
                isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Topic with id " + 1L + " not found");

        verify(postRepository, never()).save(any());

    }

    @Test
    public void addLikeSuccess() {
        try {        //given
            LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
            Topic topic = new Topic("Topic", "URL");
            User user = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");
            Post post = new Post("title", "text", user, topic);
            when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
            when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
            when(postLikeRepository.existsByPostAndUser(any(), any())).thenReturn(false);
            //when
            postService.likePost(1L, 1L);
            //then
            ArgumentCaptor<PostLike> postLikeArgumentCaptor = ArgumentCaptor.forClass(PostLike.class);
            verify(postLikeRepository).save(postLikeArgumentCaptor.capture());
            PostLike postLike = postLikeArgumentCaptor.getValue();
            assertThat(postLike).isNotNull();
            assertThat(postLike.getPost()).isEqualTo(post);
            assertThat(postLike.getUser()).isEqualTo(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void likePostShouldThrowResourceNotFound() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        User user = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));


        //when & then
        assertThatThrownBy(() -> postService.likePost(1L, 1L)).
                isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User with id " + 1L + " not found");

        assertThatThrownBy(() -> postService.likePost(2L, 1L)).
                isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post with id " + 1L + " not found");

        verify(postRepository, never()).save(any());

    }

    @Test
    public void likePostShouldThrowUserActionNotAllowed() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        Topic topic = new Topic("Topic", "URL");
        User user = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");
        Post post = new Post("title", "text", user, topic);
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(postLikeRepository.existsByPostAndUser(any(), any())).thenReturn(true);


        //when & then
        assertThatThrownBy(() -> postService.likePost(1L, 1L)).
                isInstanceOf(UserActionNotAllowedException.class)
                .hasMessageContaining("User with id " + 1L + " already like post with id " + 1L);


        verify(postRepository, never()).save(any());

    }

    @Test
    public void searchShouldReturnMergedResult() {
        // given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        Topic topic = new Topic("Java", "URL");
        User user = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");

        String query = "Java";
        List<Post> perfectMatch = new ArrayList<>();
        perfectMatch.add(new Post("Java Basics", "Introduction to Java programming", user, topic));

        List<Post> imperfectMatch = new ArrayList<>();
        imperfectMatch.add(new Post("Advanced Java", "Exploring advanced Java topics", user, topic));
        imperfectMatch.add(new Post("Java Tips and Tricks", "Useful tips for Java developers", user, topic));

        when(postRepository.findAllByTitle(query)).thenReturn(perfectMatch);
        when(postRepository.findAllByTitleContaining(query)).thenReturn(imperfectMatch);

        // when
        List<Post> result = postService.search(query);

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(3);
        assertThat(result.get(0)).isEqualTo(perfectMatch.get(0));
        assertThat(result.get(1)).isEqualTo(imperfectMatch.get(0));
        assertThat(result.get(2)).isEqualTo(imperfectMatch.get(1));

        verify(postRepository).findAllByTitle(query);
        verify(postRepository).findAllByTitleContaining(query);
    }

    @Test
    public void searchInTopicShouldReturnMergedResult() throws ResourceNotFoundException {
        // given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        Topic topic = new Topic("Java", "URL");
        User user = new User("Poseidon", dob, "URL", Gender.MALE, "auth0Id");

        String query = "Java";
        Long topicId = 1L;
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topic));

        List<Post> perfectMatch = new ArrayList<>();
        perfectMatch.add(new Post("Getting Started with Java", "Introduction to Java programming", user, topic));

        List<Post> imperfectMatch = new ArrayList<>();
        imperfectMatch.add(new Post("Advanced Java", "Exploring advanced Java topics", user, topic));
        imperfectMatch.add(new Post("Java Tips and Tricks", "Useful tips for Java developers", user, topic));

        // when
        List<Post> result = postService.searchInTopic(query, topicId);

        //then
        verify(topicRepository).findById(topicId);
        verify(postRepository).findAllByTopicAndTitle(topic, query);
        verify(postRepository).findAllByTopicAndTitleContaining(topic, query);

    }
}