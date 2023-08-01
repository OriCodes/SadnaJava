package com.forums.forum.service;

import com.forums.forum.exception.AlreadyFollowsException;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteServiceTest {
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  TopicRepository topicRepository;
    @Mock
    private  PostRepository postRepository;
    @Mock
    private  CommentRepository commentRepository;
    @Mock
    private  FollowRepository followRepository;
    @Mock
    private  MessageRepository messageRepository;
    @Mock
    private  PostLikeRepository postLikeRepository;
    @Mock
    private  CommentLikeRepository commentLikeRepository;

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
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        LocalDate dob2 = LocalDate.of(2013, Month.DECEMBER,14);
        User user1 = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        User user2 = new User("Venus", dob2, "URL", Gender.FEMALE, "Auth");
        when(userRepository.findByUserId(1L)).thenReturn(user1);
        when(userRepository.findByUserId(2L)).thenReturn(user2);
        when(followRepository.existsByFollowerAndFollowed(user1,user2)).thenReturn(true);
        //when
        deleteService.unfollow(1L,2L);
        //then
        ArgumentCaptor<User> userArgCaptor = ArgumentCaptor.forClass(User.class);
        verify(followRepository).deleteAllByFollowerAndAndFollowed(userArgCaptor.capture(), userArgCaptor.capture());
        List<User> expected = userArgCaptor.getAllValues();
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(2);
        assertThat(expected.get(0)).isEqualTo(user1);
        assertThat(expected.get(1)).isEqualTo(user2);
    }

    @Test
    public void unfollowThrowUserIdNotInSystem() {
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        User user1 = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        when(userRepository.findByUserId(1L)).thenReturn(null);
        when(userRepository.findByUserId(2L)).thenReturn(null);
        when(userRepository.findByUserId(3L)).thenReturn(user1);
        //when
        //then
        assertThatThrownBy(()-> deleteService.unfollow(1L,2L)).
                isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Follower or Followed don't exist");
        assertThatThrownBy(()-> deleteService.unfollow(1L,3L)).
                isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Follower or Followed don't exist");
        assertThatThrownBy(()-> deleteService.unfollow(3L,2L)).
                isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Follower or Followed don't exist");

        verify(followRepository, never()).deleteAllByFollowerAndAndFollowed(any(),any());
    }

    @Test
    public void unfollowThrowNotFollowing() {
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        LocalDate dob2 = LocalDate.of(2013, Month.DECEMBER,14);
        User user1 = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        User user2 = new User("Venus", dob2, "URL", Gender.FEMALE, "Auth");
        when(userRepository.findByUserId(1L)).thenReturn(user1);
        when(userRepository.findByUserId(2L)).thenReturn(user2);
        when(followRepository.existsByFollowerAndFollowed(user1,user2)).thenReturn(false);
        //when
        //then
        assertThatThrownBy(()-> deleteService.unfollow(1L,2L)).
                isInstanceOf(IllegalArgumentException.class).hasMessageContaining(
                        "User with id " + 1L + " don't follow User with id " + 2L
                );

        verify(followRepository, never()).deleteAllByFollowerAndAndFollowed(any(),any());
    }

    @Test
    public void unlikePostSuccess() {
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        Topic topic = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        Post post  = new Post("expectedTitle", "Mine is judo",timestamp,user, topic);
        when(userRepository.findByUserId(1L)).thenReturn(user);
        when(postRepository.findByPostId(1L)).thenReturn(post);
        when(postLikeRepository.existsByPostAndUser(post,user)).thenReturn(true);

        //when
        deleteService.unlikePost(1L,1L);
        //then
        ArgumentCaptor<User> userArgCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Post> postArgCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postLikeRepository).deleteAllByPostAndUser(postArgCaptor.capture(), userArgCaptor.capture());
        User expectedUser = userArgCaptor.getValue();
        Post expectedPost = postArgCaptor.getValue();
        assertThat(expectedUser).isNotNull();
        assertThat(expectedUser).isEqualTo(user);
        assertThat(expectedPost).isNotNull();
        assertThat(expectedPost).isEqualTo(post);


    }

    @Test
    public void unlikePostThrowNotInSystem() {
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        Topic topic = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        Post post  = new Post("expectedTitle", "Mine is judo",timestamp,user, topic);
        when(userRepository.findByUserId(1L)).thenReturn(null);
        when(postRepository.findByPostId(1L)).thenReturn(null);
        when(userRepository.findByUserId(2L)).thenReturn(user);
        when(postRepository.findByPostId(2L)).thenReturn(post);
        //when
        //then
        assertThatThrownBy(()-> deleteService.unlikePost(1L,2L)).
                isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Post or user don't exist");

        assertThatThrownBy(()-> deleteService.unlikePost(2L,1L)).
                isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Post or user don't exist");

        assertThatThrownBy(()-> deleteService.unlikePost(1L,1L)).
                isInstanceOf(IllegalArgumentException.class).hasMessageContaining("Post or user don't exist");

        verify(postLikeRepository, never()).deleteAllByPostAndUser(any(),any());
    }

    @Test
    public void unlikePostThrowDontLike() {
        //given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        Topic topic = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        Post post  = new Post("expectedTitle", "Mine is judo",timestamp,user, topic);
        when(userRepository.findByUserId(1L)).thenReturn(user);
        when(postRepository.findByPostId(1L)).thenReturn(post);
        when(postLikeRepository.existsByPostAndUser(post,user)).thenReturn(false);
        //when
        //then
        assertThatThrownBy(()-> deleteService.unlikePost(1L,1L)).
                isInstanceOf(IllegalArgumentException.class).hasMessageContaining(
                        "User with id " + 1L + " don't like post with id " + 1L
                );

        verify(postLikeRepository, never()).deleteAllByPostAndUser(any(),any());
    }


    @Test
    public void unlikeCommentSuccess() {
        // given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        Topic topic = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        Post post  = new Post("expectedTitle", "Mine is judo",timestamp,user, topic);
        Comment comment = new Comment("Nice post!", timestamp, user, post);

        when(userRepository.findByUserId(1L)).thenReturn(user);
        when(commentRepository.findByCommentId(1L)).thenReturn(comment);
        when(commentLikeRepository.existsByCommentAndUser(comment, user)).thenReturn(true);

        // when
        deleteService.unlikeComment(1L, 1L);

        // then
        ArgumentCaptor<User> userArgCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Comment> commentArgCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentLikeRepository).deleteAllByCommentAndUser(commentArgCaptor.capture(), userArgCaptor.capture());
        User expectedUser = userArgCaptor.getValue();
        Comment expectedComment = commentArgCaptor.getValue();
        assertThat(expectedUser).isNotNull();
        assertThat(expectedUser).isEqualTo(user);
        assertThat(expectedComment).isNotNull();
        assertThat(expectedComment).isEqualTo(comment);
    }

    @Test
    public void unlikeCommentThrowNotInSystem() {
        // given

        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        Topic topic = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        Post post  = new Post("expectedTitle", "Mine is judo",timestamp,user, topic);
        Comment comment = new Comment("Nice post!", timestamp, user, post);


        when(userRepository.findByUserId(1L)).thenReturn(null);
        when(commentRepository.findByCommentId(1L)).thenReturn(null);
        when(userRepository.findByUserId(2L)).thenReturn(user);
        when(commentRepository.findByCommentId(2L)).thenReturn(comment);

        // when & then
        assertThatThrownBy(() -> deleteService.unlikeComment(1L, 2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Comment or User don't exist");

        assertThatThrownBy(() -> deleteService.unlikeComment(2L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Comment or User don't exist");

        assertThatThrownBy(() -> deleteService.unlikeComment(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Comment or User don't exist");

        verify(commentLikeRepository, never()).deleteAllByCommentAndUser(any(), any());
    }

    @Test
    public void unlikeCommentThrowDontLike() {
        // given
        LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
        Topic topic = new Topic("Sport", new Timestamp(System.currentTimeMillis()), "URL");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        User user = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
        Post post  = new Post("expectedTitle", "Mine is judo",timestamp,user, topic);
        Comment comment = new Comment("Nice post!", timestamp, user, post);

        when(userRepository.findByUserId(1L)).thenReturn(user);
        when(commentRepository.findByCommentId(1L)).thenReturn(comment);
        when(commentLikeRepository.existsByCommentAndUser(comment, user)).thenReturn(false);

        // when & then
        assertThatThrownBy(() -> deleteService.unlikeComment(1L, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("User with id " + 1L + " don't like comment with id " + 1L);

        verify(commentLikeRepository, never()).deleteAllByCommentAndUser(any(), any());
    }
}