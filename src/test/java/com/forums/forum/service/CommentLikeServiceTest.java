package com.forums.forum.service;

import com.forums.forum.model.Comment;
import com.forums.forum.model.CommentLike;
import com.forums.forum.model.User;
import com.forums.forum.repo.CommentLikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.mockito.Mockito.verify;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class CommentLikeServiceTest {
    @Mock
    private CommentLikeRepository commentLikeRepository;
    private CommentLikeService commentLikeService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        commentLikeService = new CommentLikeService(commentLikeRepository);
    }

    @Test
    public void getNumberOfLikes() {
        //given
        Comment comment = new Comment();
        //then
        commentLikeService.getNumberOfLikes(comment);
        //when
        ArgumentCaptor<Comment> commentArgumentCaptor =  ArgumentCaptor.forClass(Comment.class);
        verify(commentLikeRepository).countAllByComment(commentArgumentCaptor.capture());
        Comment capturedComment = commentArgumentCaptor.getValue();
        assertThat(capturedComment).isEqualTo(comment);

    }

    @Test
    public void hasLiked() {
        //given
        Comment comment = new Comment();
        User user = new User();
        //then
        commentLikeService.hasLiked(user,comment);
        //when
        ArgumentCaptor<Comment> commentArgumentCaptor =  ArgumentCaptor.forClass(Comment.class);
        ArgumentCaptor<User> userArgumentCaptor =  ArgumentCaptor.forClass(User.class);
        verify(commentLikeRepository).existsByCommentAndUser(commentArgumentCaptor.capture(),userArgumentCaptor.capture());

        Comment capturedComment = commentArgumentCaptor.getValue();
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedComment).isEqualTo(comment);
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void deleteLike() {
        //given
        Comment comment = new Comment();
        User user = new User();
        //then
        commentLikeService.deleteLike(comment,user);
        //when
        ArgumentCaptor<Comment> commentArgumentCaptor =  ArgumentCaptor.forClass(Comment.class);
        ArgumentCaptor<User> userArgumentCaptor =  ArgumentCaptor.forClass(User.class);
        verify(commentLikeRepository).deleteAllByCommentAndUser(commentArgumentCaptor.capture(),userArgumentCaptor.capture());

        Comment capturedComment = commentArgumentCaptor.getValue();
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedComment).isEqualTo(comment);
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void addLike() {
        //given
        User user = new User();
        Comment comment = new Comment();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //then
        commentLikeService.addLike(user,comment,timestamp);
        //when
        ArgumentCaptor<CommentLike> commentLikeArgumentCaptor =  ArgumentCaptor.forClass(CommentLike.class);
        verify(commentLikeRepository).save(commentLikeArgumentCaptor.capture());

        CommentLike capturedCommentLike = commentLikeArgumentCaptor.getValue();
        assertThat(capturedCommentLike.getComment()).isEqualTo(comment);
        assertThat(capturedCommentLike.getUser()).isEqualTo(user);
        assertThat(capturedCommentLike.getLikeTime()).isEqualTo(timestamp);
    }
}