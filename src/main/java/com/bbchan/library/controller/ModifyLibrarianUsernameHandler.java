package com.bbchan.library.controller;

import com.bbchan.library.entity.User;
import com.bbchan.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ModifyLibrarianUsernameHandler {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/modifylibrarian_username")
    public ResponseEntity<Map<String, Object>> modifylibrarian_username(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String message;
        User user = userRepository.findByUsername((String) params.get("oldusername"));
        if (user != null && user.getIdentity() == 2) {
            User user1 = new User();
            user1.setIdentity(2);
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());
            user1.setUsername((String) params.get("newusername"));
//            user.setUsername((String) params.get("newusername"));
            userRepository.delete(user);
            userRepository.save(user1);
            message = "librarian_username修改成功！";
        } else {
            message = "该用户不存在或非librarian用户！";
        }
        res.put("statues", 400);
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
