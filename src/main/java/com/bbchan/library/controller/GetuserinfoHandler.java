package com.bbchan.library.controller;

import com.bbchan.library.entity.User;
import com.bbchan.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class GetuserinfoHandler {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/reader/info")
    public ResponseEntity<Map<String, Object>> searchreader(@RequestParam(value = "username") String username) {
        Map<String, Object> res = new HashMap<>();
//        String username = (String) params.get("username");
        User user = userRepository.findByUsername(username);
        String message;
        if (user == null) {//无记录
            message = "该用户不存在！";
            res.put("status", 400);
            res.put("message", message);
        } else {
            res.put("status", 200);
            res.put("user_info", user);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
