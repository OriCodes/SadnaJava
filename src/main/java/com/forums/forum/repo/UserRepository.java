package com.forums.forum.repo;

import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUserName(String userName);
    User findByUserName(String userNAme);

    List<User> findAllByUserNameContaining(String userName);
    List<User> findAllByGender(Gender gender);

}
