package com.forums.forum.service;

import com.forums.forum.model.*;
import com.forums.forum.repo.CommentLikeRepository;
import com.forums.forum.repo.CommentRepository;
import com.forums.forum.repo.PostRepository;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentLikeRepository commentLikeRepository;

    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        commentService = new CommentService(
                commentRepository,
                userRepository,
                postRepository,
                commentLikeRepository
        );
    }

    @Test
    public void getAllCommentsForPost() {
    }

    @Test
    public void getAllCommentsByUser() {
    }

    @Test
    public void getNumberOfCommentsForPost() {
    }

    @Test
    public void getNumberOfCommentsByUser() {
    }

    @Test
    public void addComment() {
    }

    @Test
    public void getNumberOfLikes() {
    }

    @Test
    public void hasLiked() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        User user = new User("Poseidon", dob, "profileURL", Gender.MALE, "auth0Id");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Comment comment = new Comment("Text", user, new Post());
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        //when
        commentService.hasLiked(1L, 2L);
        //then
        ArgumentCaptor<Comment> commentArgCaptor = ArgumentCaptor.forClass(Comment.class);
        ArgumentCaptor<User> userArgCaptor = ArgumentCaptor.forClass(User.class);
        verify(commentLikeRepository).existsByCommentAndUser(commentArgCaptor.capture(), userArgCaptor.capture());
        User capturedUser = userArgCaptor.getValue();
        Comment capturedComment = commentArgCaptor.getValue();

        assertThat(capturedUser).isNotNull();
        assertThat(capturedComment).isNotNull();
        assertThat(capturedUser).isEqualTo(user);
        assertThat(capturedComment).isEqualTo(comment);

    }

    @Test
    public void likeComment() {
        //given
        LocalDate dob = LocalDate.of(1999, Month.APRIL, 7);
        User user = new User("Poseidon", dob, "profileURL", Gender.MALE, "auth0Id");
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Comment comment = new Comment("Text", user, new Post());
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        //then
        ArgumentCaptor<CommentLike> commentLikeArgumentCaptor = ArgumentCaptor.forClass(CommentLike.class);
        verify(commentLikeRepository).save(commentLikeArgumentCaptor.capture());

        CommentLike capturedCommentLike = commentLikeArgumentCaptor.getValue();
        assertThat(capturedCommentLike.getComment()).isEqualTo(comment);
        assertThat(capturedCommentLike.getUser()).isEqualTo(user);

    }
}