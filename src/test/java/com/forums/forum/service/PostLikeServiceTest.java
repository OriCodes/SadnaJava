package com.forums.forum.service;

import com.forums.forum.model.Post;
import com.forums.forum.model.PostLike;
import com.forums.forum.model.User;
import com.forums.forum.repo.PostLikeRepository;
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
class PostLikeServiceTest {
    @Mock
    private PostLikeRepository postLikeRepository;
    private PostLikeService postLikeService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        postLikeService = new PostLikeService(postLikeRepository);
    }

    @Test
    public void getNumberOfLikes() {
        //given
        Post post = new Post();
        //when
        postLikeService.getNumberOfLikes(post);
        //then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class) ;
        verify(postLikeRepository).countAllByPost(postArgumentCaptor.capture());

        Post capturedPost = postArgumentCaptor.getValue();
        assertThat(capturedPost).isEqualTo(post);

    }

    @Test
    public void hasLiked() {
        //given
        Post post = new Post();
        User user = new User();
        //when
        postLikeService.hasLiked(user,post);
        //then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class) ;
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(postLikeRepository).existsByPostAndUser(postArgumentCaptor.capture(), userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        Post capturedPost = postArgumentCaptor.getValue();
        assertThat(capturedPost).isEqualTo(post);
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void deleteLike() {
        //given
        Post post = new Post();
        User user = new User();
        //when
        postLikeService.deleteLike(post,user);
        //then
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class) ;
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class) ;
        verify(postLikeRepository).deleteAllByPostAndUser(postArgumentCaptor.capture(), userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();
        Post capturedPost = postArgumentCaptor.getValue();
        assertThat(capturedPost).isEqualTo(post);
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void addLike() {
        //given
        User user = new User();
        Post post = new Post();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //when
        postLikeService.addLike(user,post,timestamp);
        //then
        ArgumentCaptor<PostLike> postLikeArgumentCaptor = ArgumentCaptor.forClass(PostLike.class) ;
        verify(postLikeRepository).save(postLikeArgumentCaptor.capture());

        PostLike capturedPostLike = postLikeArgumentCaptor.getValue();
        assertThat(capturedPostLike.getLikeTime()).isEqualTo(timestamp);
        assertThat(capturedPostLike.getPost()).isEqualTo(post);
        assertThat(capturedPostLike.getUser()).isEqualTo(user);
    }
}