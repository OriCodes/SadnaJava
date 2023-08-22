package com.forums.forum.repo;

import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUserName(String userName);
    User findByUserName(String userNAme);

    List<User> findAllByUserName(String userName);
    List<User> findAllByUserNameContaining(String seq);
    List<User> findAllByGender(Gender gender);

    Optional<User> findByEmail(String email);

}
