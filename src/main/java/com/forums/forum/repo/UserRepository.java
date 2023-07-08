package com.forums.forum.repo;

import com.forums.forum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    boolean existsByUserName(String userName);

}
