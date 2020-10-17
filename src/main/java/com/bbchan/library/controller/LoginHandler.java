package com.bbchan.library.controller;

import com.bbchan.library.entity.User;
import com.bbchan.library.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginHandler {
    @Autowired
    private AccountService accountService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String message;
        User user = accountService.findUser((String) params.get("username"));
        if (user == null) {
            message = "用户不存在";
            res.put("statues", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        if (user.getIdentity() != 2 && user.getIdentity() != 3) {
            message = "登录失败，用户身份非法";
            res.put("statues", 402);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        if (user.getPassword().equals((String) params.get("password"))) {
            if (user.getIdentity() == 2) {
                message = "登录成功，身份为librarian";
                res.put("statues", 200);
            } else {
                message = "登录成功，身份为reader";
                res.put("statues", 201);
            }
        } else {
            message = "登录失败，密码错误";
            res.put("statues", 403);
        }
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
