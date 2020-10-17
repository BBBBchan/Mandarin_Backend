package com.bbchan.library.repository;

import com.bbchan.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    List<User> findAllByUsernameAndEmail(String username, String email);

    List<User> findAllByUsername(String username);

    List<User> findAllByIdentity(Integer identity);

}
