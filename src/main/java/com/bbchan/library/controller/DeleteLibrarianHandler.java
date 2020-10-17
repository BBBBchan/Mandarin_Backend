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
public class DeleteLibrarianHandler {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/deletelibrarian")
    public ResponseEntity<Map<String, Object>> deletelibrarian(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String message;
        User user = userRepository.findByUsername((String) params.get("username"));
        if (user != null && user.getIdentity() == 2) {
            userRepository.delete(user);
            message = "librarian删除成功！";
        } else {
            message = "该用户不存在或非librarian用户！";
        }
        res.put("statues", 200);
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
