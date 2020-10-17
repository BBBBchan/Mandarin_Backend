package com.bbchan.library.service;

import com.bbchan.library.entity.User;
import com.bbchan.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private UserRepository userRepository;
//    public Boolean check_login(String username, String password){
//        User user = userRepository.findByUsername(username);
//        return password.equals(user.getUsername());
//    }

    public User findUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllByUsername(String username) {
        return userRepository.findAllByUsername(username);
    }

    public List<User> findAllByUsernameAndEmail(String username, String email) {
        return userRepository.findAllByUsernameAndEmail(username, email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllByIdentity(Integer identity) {
        return userRepository.findAllByIdentity(identity);
    }
}
