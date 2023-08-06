package com.forums.forum.config;

import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import com.forums.forum.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class UserConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository){
        return  args -> {
            LocalDate dob1 = LocalDate.of(2003, Month.DECEMBER,14);
            LocalDate dob2 = LocalDate.of(2013, Month.DECEMBER,14);
            User user1 = new User("Poseidon", dob1, "URL", Gender.MALE, "Auth");
            User user2 = new User("Venus", dob2, "URL", Gender.FEMALE, "Auth");
            userRepository.saveAll(List.of(user1, user2));
        };
    }
}
