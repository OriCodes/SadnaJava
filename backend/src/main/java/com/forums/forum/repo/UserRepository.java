package com.forums.forum.repo;

import com.forums.forum.model.Gender;
import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUsername(String userName);
    Optional<User> findByUsername(String userNAme);

    List<User> findAllByUsername(String userName);
    List<User> findAllByUsernameContaining(String seq);
    List<User> findAllByGender(Gender gender);
}
