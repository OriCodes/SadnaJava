//package com.forums.forum.service;
//
//import com.forums.forum.model.Gender;
//import com.forums.forum.model.Post;
//import com.forums.forum.model.Topic;
//import com.forums.forum.model.User;
//import com.forums.forum.repo.PostLikeRepository;
//import com.forums.forum.repo.PostRepository;
//import com.forums.forum.repo.TopicRepository;
//import com.forums.forum.repo.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class PostServiceTest {
//
//    @Mock
//    private PostRepository postRepository;
//    @Mock
//    private PostLikeRepository postLikeRepository;
//    @Mock
//    private  UserRepository userRepository;
//    @Mock
//    private  TopicRepository topicRepository;
//    private PostService postService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//        postService = new PostService(postRepository,postLikeRepository,userRepository,topicRepository);
//    }
//
//    @Test
//    public void byId() {
//        //given
//        Post post = new Post();
//        Long validId = 1L, notValidId = 2L;
//        post.setPostId(validId);
//        when(postRepository.findById(validId)).thenReturn(Optional.of(post));
//        when(postRepository.findById(notValidId)).thenReturn(Optional.empty());
//        //when
//        Post valid = postService.byId(validId);
//        Post notValid = postService.byId(notValidId);
//        //then
//        assertThat(valid).isNotNull();
//        assertThat(valid.getPostId()).isEqualTo(validId);
//        assertThat(notValid).isNull();
//    }
//
//    @Test
//    public void byTitle() {
//        //given
//        String title = "title";
//        //when
//        postService.byTitle(title);
//        //then
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        verify(postRepository).findByTitle(stringArgumentCaptor.capture());
//        String capturedTitle = stringArgumentCaptor.getValue();
//        assertThat(capturedTitle).isEqualTo(title);
//    }
//
//    @Test
//    public void byTitleAndTopic() {
//        //given
//        String title = "title";
//        Topic topic = new Topic("Topic",new Timestamp(System.currentTimeMillis()),"URL");
//        //when
//        postService.byTitleAndTopic(title,topic);
//        //then
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
//        verify(postRepository).findByTitleAndTopic(stringArgumentCaptor.capture(),topicArgumentCaptor.capture());
//        String capturedTitle = stringArgumentCaptor.getValue();
//        Topic capturedTopic = topicArgumentCaptor.getValue();
//        assertThat(capturedTitle).isNotNull();
//        assertThat(capturedTitle).isEqualTo(title);
//        assertThat(capturedTopic).isNotNull();
//        assertThat(capturedTopic).isEqualTo(topic);
//    }
//
//    @Test
//    public void isExistByTitle() {
//        //given
//        String title = "title";
//        //when
//        postService.isExistByTitle(title);
//        //then
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        verify(postRepository).existsByTitle(stringArgumentCaptor.capture());
//        String capturedTitle = stringArgumentCaptor.getValue();
//        assertThat(capturedTitle).isEqualTo(title);
//    }
//
//    @Test
//    public void isExistByTitleAndTopic() {
//        //given
//        String title = "title";
//        Topic topic = new Topic("Topic",new Timestamp(System.currentTimeMillis()),"URL");
//        //when
//        postService.isExistByTitleAndTopic(title,topic);
//        //then
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
//        verify(postRepository).existsByTitleAndTopic(stringArgumentCaptor.capture(),topicArgumentCaptor.capture());
//        String capturedTitle = stringArgumentCaptor.getValue();
//        Topic capturedTopic = topicArgumentCaptor.getValue();
//        assertThat(capturedTitle).isNotNull();
//        assertThat(capturedTitle).isEqualTo(title);
//        assertThat(capturedTopic).isNotNull();
//        assertThat(capturedTopic).isEqualTo(topic);
//    }
//
//    @Test
//    public void allPosts() {
//        //given
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
//        User user1 = new User("Poseidon", dob, "URL", Gender.MALE,"auth0Id");
//        User user2 = new User("Venus", dob, "URL",Gender.FEMALE,"auth0Id");
//        Topic topic = new Topic("Topic",timestamp,"URL");
//        Post post1 = new Post("title","text", timestamp, user1, topic);
//        Post post2 = new Post("title","text", timestamp, user2, topic);
//        List<Post> expecList = Arrays.asList(post1,post2);
//        when(postRepository.findAll()).thenReturn(expecList);
//        //then
//        List<Post>resList = postService.allPosts();
//        //then
//        assertThat(resList).isNotNull();
//        assertThat(resList.get(0)).isEqualTo(post1);
//        assertThat(resList.get(1)).isEqualTo(post2);
//    }
//
//    @Test
//    public void allByTitle() {
//        //given
//        String title = "title";
//        //when
//        postService.allByTitle(title);
//        //then
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        verify(postRepository).findAllByTitle(stringArgumentCaptor.capture());
//        String capturedTitle = stringArgumentCaptor.getValue();
//        assertThat(capturedTitle).isEqualTo(title);
//    }
//
//    @Test
//    public void allByTitleContaining() {
//        //given
//        String seq = "seq";
//        //when
//        postService.allByTitleContaining(seq);
//        //then
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        verify(postRepository).findAllByTitleContaining(stringArgumentCaptor.capture());
//        String capturedSeq = stringArgumentCaptor.getValue();
//        assertThat(capturedSeq).isEqualTo(seq);
//    }
//
//    @Test
//    public void allByTopicAndTitleContaining() {
//        //given
//        String seq = "seq";
//        Topic topic = new Topic("Topic",new Timestamp(System.currentTimeMillis()),"URL");
//        //when
//        postService.allByTopicAndTitleContaining(topic,seq);
//        //then
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
//        verify(postRepository).findAllByTopicAndTitleContaining(
//                topicArgumentCaptor.capture(),
//                stringArgumentCaptor.capture()
//        );
//        String capturedSeq = stringArgumentCaptor.getValue();
//        Topic capturedTopic = topicArgumentCaptor.getValue();
//        assertThat(capturedTopic).isNotNull();
//        assertThat(capturedSeq).isNotNull();
//        assertThat(capturedSeq).isEqualTo(seq);
//        assertThat(capturedTopic).isEqualTo(topic);
//    }
//
//    @Test
//    public void allByTopicAndTitle() {
//        //given
//        String title = "title";
//        Topic topic = new Topic("Topic",new Timestamp(System.currentTimeMillis()),"URL");
//        //when
//        postService.allByTopicAndTitle(topic,title);
//        //then
//        ArgumentCaptor<String> stringArgumentCaptor = ArgumentCaptor.forClass(String.class);
//        ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
//        verify(postRepository).findAllByTopicAndTitle(
//                topicArgumentCaptor.capture(),
//                stringArgumentCaptor.capture()
//        );
//        String capturedTitle = stringArgumentCaptor.getValue();
//        Topic capturedTopic = topicArgumentCaptor.getValue();
//        assertThat(capturedTopic).isNotNull();
//        assertThat(capturedTitle).isNotNull();
//        assertThat(capturedTitle).isEqualTo(title);
//        assertThat(capturedTopic).isEqualTo(topic);
//    }
//
//    @Test
//    public void allByUser() {
//        //given
//        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
//        User user = new User("Poseidon", dob, "URL", Gender.MALE,"auth0Id");
//        //when
//        postService.allByUser(user);
//        //then
//        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        verify(postRepository).findAllByUser(userArgumentCaptor.capture());
//        User capturedUser = userArgumentCaptor.getValue();
//
//        assertThat(capturedUser).isNotNull();
//        assertThat(capturedUser).isEqualTo(user);
//    }
//
//    @Test
//    public void allByTopic() {
//        //given
//        Topic topic = new Topic("Topic",new Timestamp(System.currentTimeMillis()),"URL");
//        //when
//        postService.allByTopic(topic);
//        //then
//        ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
//        verify(postRepository).findAllByTopic(topicArgumentCaptor.capture());
//        Topic capturedTopic = topicArgumentCaptor.getValue();
//
//        assertThat(capturedTopic).isNotNull();
//        assertThat(capturedTopic).isEqualTo(topic);
//    }
//
//    @Test
//    public void allByUserAndTopic() {
//        //given
//        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
//        Topic topic = new Topic("Topic",new Timestamp(System.currentTimeMillis()),"URL");
//        User user = new User("Poseidon", dob, "URL", Gender.MALE,"auth0Id");
//        //when
//        postService.allByUserAndTopic(user,topic);
//        //then
//        ArgumentCaptor<Topic> topicArgumentCaptor = ArgumentCaptor.forClass(Topic.class);
//        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        verify(postRepository).findAllByTopicAndUser(
//                topicArgumentCaptor.capture(),
//                userArgumentCaptor.capture()
//        );
//
//        Topic capturedTopic = topicArgumentCaptor.getValue();
//        User capturedUser = userArgumentCaptor.getValue();
//
//        assertThat(capturedTopic).isNotNull();
//        assertThat(capturedTopic).isEqualTo(topic);
//        assertThat(capturedUser).isNotNull();
//        assertThat(capturedUser).isEqualTo(user);
//
//    }
//
//    @Test
//    public void addPost() {
//        //given
//        LocalDate dob = LocalDate.of(1999, Month.APRIL,7);
//        Topic topic = new Topic("Topic",new Timestamp(System.currentTimeMillis()),"URL");
//        User user = new User("Poseidon", dob, "URL", Gender.MALE,"auth0Id");
//        String title = "title" , text = "text";
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        //when
//        postService.addPost(user,topic,title,text,timestamp);
//        //then
//        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
//        verify(postRepository).save(postArgumentCaptor.capture());
//
//        Post capturedPost = postArgumentCaptor.getValue();
//        assertThat(capturedPost).isNotNull();
//        assertThat(capturedPost.getTitle()).isEqualTo(title);
//        assertThat(capturedPost.getText()).isEqualTo(text);
//        assertThat(capturedPost.getUser()).isEqualTo(user);
//        assertThat(capturedPost.getCreatedTimeStamp()).isEqualTo(timestamp);
//
//    }
//}