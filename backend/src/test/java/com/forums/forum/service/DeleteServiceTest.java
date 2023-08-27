package com.forums.forum.service;

import com.forums.forum.exception.ResourceNotFoundException;
import com.forums.forum.exception.UserActionNotAllowedException;
import com.forums.forum.model.*;
import com.forums.forum.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private TopicRepository topicRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private FollowRepository followRepository;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private PostLikeRepository postLikeRepository;
    @Mock
    private CommentLikeRepository commentLikeRepository;

    private DeleteService deleteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteService = new DeleteService(
                userRepository,
                topicRepository,
                postRepository,
                commentRepository,
                followRepository,
                messageRepository,
                postLikeRepository,
                commentLikeRepository
        );
    }

    @Test
    public void unfollowSuccess() {
        try{        //given
            LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
            LocalDate dob2 = LocalDate.of(2013, Month.DECEMBER, 14);
            User user1 = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
            User user2 = new User("Venus", dob2, "URL", Gender.FEMALE, "Auth");
            when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
            when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
            when(followRepository.existsByFollowerAndFollowed(user1, user2)).thenReturn(true);
            //when
            deleteService.unfollow(1L, 2L);
            //then
            ArgumentCaptor<User> userArgCaptor = ArgumentCaptor.forClass(User.class);
            verify(followRepository).deleteAllByFollowerAndAndFollowed(userArgCaptor.capture(), userArgCaptor.capture());
            List<User> expected = userArgCaptor.getAllValues();
            assertThat(expected).isNotNull();
            assertThat(expected.size()).isEqualTo(2);
            assertThat(expected.get(0)).isEqualTo(user1);
            assertThat(expected.get(1)).isEqualTo(user2);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void unfollowThrowUserIdNotInSystem() {
        //given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        when(userRepository.findById(3L)).thenReturn(Optional.of(new User()));
        //when
        //then
        assertThatThrownBy(() -> deleteService.unfollow(1L, 2L)).
                isInstanceOf(ResourceNotFoundException.class);

        assertThatThrownBy(() -> deleteService.unfollow(1L, 3L)).
                isInstanceOf(ResourceNotFoundException.class);

        assertThatThrownBy(() -> deleteService.unfollow(3L, 2L)).
                isInstanceOf(ResourceNotFoundException.class);

        verify(followRepository, never()).deleteAllByFollowerAndAndFollowed(any(), any());
        verify(followRepository, never()).existsByFollowerAndFollowed(any(), any());
    }

    @Test
    public void unfollowThrowNotFollowing() {
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
        LocalDate dob2 = LocalDate.of(2013, Month.DECEMBER, 14);
        User user1 = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        User user2 = new User("Venus", dob2, "URL", Gender.FEMALE, "Auth");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user2));
        when(followRepository.existsByFollowerAndFollowed(user1, user2)).thenReturn(false);
        //when
        //then
        assertThatThrownBy(() -> deleteService.unfollow(1L, 2L)).
                isInstanceOf(UserActionNotAllowedException.class).hasMessageContaining(
                        "User with id " + 1L + " don't follow User with id " + 2L
                );

        verify(followRepository, never()).deleteAllByFollowerAndAndFollowed(any(), any());
    }


    @Test
    public void unlikePostSuccess() {
        try{        // given
            LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
            Topic topic = new Topic("Sport", "URL");
            Timestamp timestamp;
            User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
            Post post = new Post("expectedTitle", "Mine is judo", user, topic);

            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(postRepository.findById(1L)).thenReturn(Optional.of(post));
            when(postLikeRepository.existsByPostAndUser(post, user)).thenReturn(true);

            // when
            deleteService.unlikePost(1L, 1L);

            // then
            verify(postLikeRepository).deleteAllByPostAndUser(post, user);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void unlikePostThrowUserNotFound() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deleteService.unlikePost(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User with id 1 not found");

        verify(postLikeRepository, never()).deleteAllByPostAndUser(any(), any());
    }

    @Test
    public void unlikePostThrowPostNotFound() {
        // given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deleteService.unlikePost(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Post with id 1 not found");

        verify(postLikeRepository, never()).deleteAllByPostAndUser(any(), any());
    }

    @Test
    public void unlikePostThrowNotLiked() {
        // given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
        Topic topic = new Topic("Sport", "URL");
        Timestamp timestamp;
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        Post post = new Post("expectedTitle", "Mine is judo", user, topic);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(postRepository.findById(1L)).thenReturn(Optional.of(post));
        when(postLikeRepository.existsByPostAndUser(post, user)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> deleteService.unlikePost(1L, 1L))
                .isInstanceOf(UserActionNotAllowedException.class)
                .hasMessageContaining("User with id 1 don't like post with id 1");

        verify(postLikeRepository, never()).deleteAllByPostAndUser(any(), any());
    }

    @Test
    public void unlikeCommentSuccess() {
        try{     // given
            LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
            Topic topic = new Topic("Sport", "URL");
            Timestamp timestamp;
            User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
            Post post = new Post("expectedTitle", "Mine is judo", user, topic);
            Comment comment = new Comment("Nice post!", user, post);

            when(userRepository.findById(1L)).thenReturn(Optional.of(user));
            when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
            when(commentLikeRepository.existsByCommentAndUser(comment, user)).thenReturn(true);

            // when
            deleteService.unlikeComment(1L, 1L);

            // then
            verify(commentLikeRepository).deleteAllByCommentAndUser(comment, user);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void unlikeCommentThrowUserNotFound() {
        // given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deleteService.unlikeComment(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("User with id 1 not found");

        verify(commentLikeRepository, never()).deleteAllByCommentAndUser(any(), any());
    }

    @Test
    public void unlikeCommentThrowCommentNotFound() {
        // given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.findById(1L)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> deleteService.unlikeComment(1L, 1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Comment with id 1 not found");

        verify(commentLikeRepository, never()).deleteAllByCommentAndUser(any(), any());
    }

    @Test
    public void unlikeCommentThrowNotLiked() {
        // given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
        Topic topic = new Topic("Sport", "URL");
        Timestamp timestamp;
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        Post post = new Post("expectedTitle", "Mine is judo", user, topic);
        Comment comment = new Comment("Nice post!", user, post);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentLikeRepository.existsByCommentAndUser(comment, user)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> deleteService.unlikeComment(1L, 1L))
                .isInstanceOf(UserActionNotAllowedException.class)
                .hasMessageContaining("User with id 1 don't like comment with id 1");

        verify(commentLikeRepository, never()).deleteAllByCommentAndUser(any(), any());
    }
}