package com.forums.forum.repo;

import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private  UserRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }
    @Test
    void existsByUserName() {
        //given
        User user = new User("Poseidon", 19, "URL", Gender.MALE, "Auth");
        underTest.save(user);
        //when
        boolean expectedTrue = underTest.existsByUserName("Poseidon");
        boolean expectedFalse = underTest.existsByUserName("Venus");
        //then
        assertThat(expectedTrue).isTrue();
        assertThat(expectedFalse).isFalse();
    }

    @Test
    void findByUserName() {

    }

    @Test
    void findAllByUserNameContaining() {
    }

    @Test
    void findAllByGender() {
    }
}