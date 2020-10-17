package com.bbchan.library.controller;

import com.bbchan.library.entity.User;
import com.bbchan.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reader")
public class ModifyReaderHandler {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/modify")
    public ResponseEntity<Map<String, Object>> modifyInformation(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String message;
        //要返回的
        User M_user = userRepository.findByUsername((String) params.get("username"));
        if (M_user == null) {
            message = "用户不存在";
            res.put("statues", 402);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        if (M_user.getIdentity() != 3) {
            message = "认证失败，用户权限错误";
            res.put("statues", 403);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        String password = (String) params.get("password");
        if (!M_user.getPassword().equals(password)) {
            message = "密码错误";
            res.put("statues", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        M_user.setEmail((String) params.get("email"));
        User result = userRepository.save(M_user);
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