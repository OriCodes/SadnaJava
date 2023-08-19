package com.forums.forum.config;

import com.forums.forum.model.Gender;
import com.forums.forum.model.Topic;
import com.forums.forum.model.User;
import com.forums.forum.repo.TopicRepository;
import com.forums.forum.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class PostConfig {


    @Bean
    CommandLineRunner postCommandLineRunner(TopicRepository topicRepository, UserRepository userRepository) {
        return args -> {

            LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
            LocalDate dob2 = LocalDate.of(2013, Month.DECEMBER, 14);
            User user1 = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
            User user2 = new User("Venus", dob2, "URL", Gender.FEMALE, "Auth");
            userRepository.saveAll(List.of(user1, user2));


            Topic topic1 = new Topic("Football", "./assets/images/football.jpg");
            Topic topic2 = new Topic("Gaming", "./assets/images/gaming.jpg");
            Topic topic3 = new Topic("Gaming 2", "./assets/images/gaming.jpg");
            Topic topic4 = new Topic("Gaming 3", "./assets/images/gaming.jpg");
            Topic topic5 = new Topic("Gaming 4", "./assets/images/gaming.jpg");
            Topic topic6 = new Topic("Gaming 4", "./assets/images/gaming.jpg");

            topicRepository.saveAll(List.of(topic1, topic2, topic3, topic4, topic5, topic6));

//
//            Post post1 = new Post("Post Title 1", "Post Text 1", user1, topic1);
//            Post post2 = new Post("Post Title 2", "Post Text 2", user2, topic2);
//            postRepository.saveAll(List.of(post1, post2));
        };
    }
}
