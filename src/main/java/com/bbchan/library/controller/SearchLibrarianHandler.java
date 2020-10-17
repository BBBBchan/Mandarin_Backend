package com.bbchan.library.controller;

import com.bbchan.library.entity.User;
import com.bbchan.library.repository.UserRepository;
import com.bbchan.library.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchLibrarianHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;

    @GetMapping("/librarianinfo")
    public ResponseEntity<Map<String, Object>> Returninformation(@RequestParam(value = "username") String username) {
        Map<String, Object> res = new HashMap<>();
        //要返回的
        String message;
        System.out.println(username);
        //修改个人信息需要经过账号和密码认证
        List<User> userList;
        if (!username.isEmpty()) {
            userList = accountService.findAllByUsername(username);
            if (userList.isEmpty()) {
                message = "用户不存在";
                res.put("statues", 401);
                res.put("message", message);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            if (userList.get(0).getIdentity() != 2) {
                message = "认证失败，用户权限错误";
                res.put("statues", 403);
                res.put("message", message);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            res.put("status", 200);
            res.put("userList", userList);
        } else {
            userList = accountService.findAllByIdentity(2);
            if (userList.isEmpty()) {
                message = "无有效信息";
                res.put("statues", 402);
                res.put("message", message);
            } else {
                res.put("status", 200);
                res.put("userList", userList);
            }
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
