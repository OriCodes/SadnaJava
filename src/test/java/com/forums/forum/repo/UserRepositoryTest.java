package com.forums.forum.repo;

import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

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
    public void existsByUserName() {
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
    public void findByUserName()
    {
        //given
        String userName = "Poseidon";
        User user = new User(userName, 19, "URL", Gender.MALE, "Auth");
        underTest.save(user);
        //when
        User foundUser = underTest.findByUserName(userName);
        //then
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUserName()).isEqualTo(userName);
    }

    @Test
    public void findAllByUserNameContaining() {
        //given
        User user1 = new User("Poseidon", 19, "URL", Gender.MALE, "Auth");
        User user2 = new User("Venus", 10, "URL", Gender.FEMALE, "Auth");
        underTest.saveAll(List.of(user1, user2));
        String seq = "on";
        //when
        List<User> expected = underTest.findAllByUserNameContaining(seq);
        //then
        assertThat(expected.size()).isEqualTo(1);
        assertThat(expected.get(0).getUserName().contains(seq)).isTrue();
    }

    @Test
    public void findAllByGender() {
        //given
        User user1 = new User("Poseidon", 19, "URL", Gender.MALE, "Auth");
        User user2 = new User("Venus", 10, "URL", Gender.FEMALE, "Auth");
        underTest.saveAll(List.of(user1, user2));
        Gender expectedGender = Gender.FEMALE;
        //when
        List<User> expectedUserList = underTest.findAllByGender(expectedGender);
        //then
        assertThat(expectedUserList.size()).isEqualTo(1);
        assertThat(expectedUserList.get(0).getGender()).isEqualTo(expectedGender);

    }
}