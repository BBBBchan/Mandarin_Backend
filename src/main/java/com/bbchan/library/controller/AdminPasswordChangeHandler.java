package com.bbchan.library.controller;

import com.bbchan.library.entity.User;
import com.bbchan.library.repository.UserRepository;
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
public class AdminPasswordChangeHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;

    @PostMapping("/admin-password-modify")
    public ResponseEntity<Map<String, Object>> Updateinformation(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        //要返回的
        String message;
//        修改个人信息需要经过账号和密码认证
        User user = accountService.findUser((String) params.get("username"));
        if (user == null) {
            message = "用户不存在";
            res.put("statues", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        if (!user.getPassword().equals(params.get("oldpassword"))) {
            message = "认证失败，当前密码错误";
            res.put("statues", 402);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
//        user.setUsername((String) params.get("username"));
//        update_user.setEmail((String)params.get("email"));
        if (user.getIdentity() != 1) {
            message = "认证失败，用户权限错误";
            res.put("statues", 403);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        user.setPassword((String) params.get("newpassword"));
        User result = accountService.save(user);
        if (result != null) {
            message = "修改成功";
            res.put("statues", 200);
        } else {
            message = "修改失败";
            res.put("statues", 400);
        }
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
