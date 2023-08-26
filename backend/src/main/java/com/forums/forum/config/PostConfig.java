package com.forums.forum.config;

import com.forums.forum.model.*;
import com.forums.forum.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class PostConfig {


    @Bean
    CommandLineRunner postCommandLineRunner(TopicRepository topicRepository, UserRepository userRepository, PostRepository postRepository, PostLikeRepository postLikeRepository, CommentRepository commentRepository) {
        return args -> {

            LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER, 14);
            LocalDate dob2 = LocalDate.of(2013, Month.DECEMBER, 14);
            User user1 = new User("Amit", dob1, "https://api.dicebear.com/6.x/open-peeps/svg?seed=Amit", Gender.MALE, "$2a$10$/YcE1Ahw4tnUUr/zYofhoeq2sSt.9dh816p.SwWadCE66nCTBhLTK");
            User user2 = new User("Venus", dob2, "https://api.dicebear.com/6.x/open-peeps/svg?seed=Venus", Gender.FEMALE, "$2a$10$/YcE1Ahw4tnUUr/zYofhoeq2sSt.9dh816p.SwWadCE66nCTBhLTK");
            user1.setRole(Role.USER);
            user2.setRole(Role.USER);
            userRepository.saveAll(List.of(user1, user2));


            Topic topic1 = new Topic("Football", "/assets/images/football.jpg");
            Topic topic2 = new Topic("Gaming", "/assets/images/gaming.jpg");

            topicRepository.saveAll(List.of(topic1, topic2));


            Post post1 = new Post("Exciting Football Match Last Night!", "Hey everyone, did you catch the thrilling football match last night? The game went into overtime, and the final score was 3-2. The winning goal in the last minute had everyone on the edge of their seats. What were your favorite moments from the game?", user1, topic1);
            Post post2 = new Post("Recommendations for New Gamers", "Hello fellow gamers, I'm new to the gaming world and looking for recommendations on where to start. I enjoy adventure and strategy games, but I'm open to trying different genres too. Any suggestions for must-play games for someone just starting out?", user2, topic2);

            Post post3 = new Post("Favorite Soccer Players", "Hey everyone, I'm a big fan of soccer and I'm curious to know who your favorite soccer players are. Whether it's Messi's incredible dribbling or Ronaldo's amazing goal-scoring record, share your thoughts on the players who inspire you!", user1, topic1);

            Post post4 = new Post("Looking for Co-op Partner", "Greetings gamers! I'm currently playing a co-op RPG and I'm in search of a reliable co-op partner. The game involves strategic battles and teamwork. If you're interested, let's team up and conquer the virtual world together!", user2, topic2);

            Post post5 = new Post("Tactical Analysis of Recent Match", "Hello fellow football enthusiasts, let's dive into the tactical aspects of the recent match. The way the teams set up their formations and adapted during different phases of the game was truly fascinating. Share your insights on the strategies employed by both sides!", user1, topic1);

            Post post6 = new Post("Retro Gaming Memories", "Nostalgia alert! Let's take a trip down memory lane and reminisce about the classic games we grew up playing. From pixelated graphics to iconic soundtracks, which retro games hold a special place in your heart? Share your favorite gaming memories!", user2, topic2);

            Post post7 = new Post("Upcoming Football Transfers", "Hey folks, transfer season is here! Any reliable sources or rumors about potential football transfers? Let's discuss the potential impact of these transfers on the teams and the league as a whole. Speculations, predictions, and insights are all welcome!", user1, topic1);

            postRepository.saveAll(List.of(post1, post2, post3, post4, post5, post6, post7));

            PostLike postLike1 = new PostLike(user1, post1);
            PostLike postLike2 = new PostLike(user2, post1);
            PostLike postLike3 = new PostLike(user1, post2);
            PostLike postLike4 = new PostLike(user2, post2);
            PostLike postLike5 = new PostLike(user1, post3);

            postLikeRepository.saveAll(List.of(postLike1, postLike2, postLike3, postLike4, postLike5));

            Comment comment1 = new Comment("I agree, it was a great match!", user1, post1);
            Comment comment2 = new Comment("I think the winning goal was a bit lucky, but it was still a great game overall.", user2, post1);
            Comment comment3 = new Comment("I'm not a big fan of soccer, but I do enjoy watching the World Cup.", user1, post3);
            Comment comment4 = new Comment("I'm a big fan of retro games too! My favorite is Super Mario Bros.", user2, post6);

            commentRepository.saveAll(List.of(comment1, comment2, comment3, comment4));

        };
    }
}
