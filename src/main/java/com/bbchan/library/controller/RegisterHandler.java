package com.bbchan.library.controller;

import com.bbchan.library.entity.User;
import com.bbchan.library.service.AccountService;
import com.bbchan.library.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class RegisterHandler {
    @Autowired
    private AccountService accountService;
    @Autowired
    private IncomeService incomeService;

    @PostMapping("/register-reader")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String username = (String) params.get("username");
        String email = (String) params.get("email");
        String password = (String) params.get("password");
        String message;
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        String Email = "[a-zA-Z0-9]{3,20}@[a-zA-Z0-9]{2,10}[.](com|cn|net)";
        if (username.isEmpty() || email.isEmpty()) {
            message = "获取信息失败";
            res.put("statues", 400);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(username);
            if (m.matches() && email.matches(Email)) {
                List<User> us = accountService.findAllByUsernameAndEmail(username, email);
                if (us.size() == 0) {
                    User registers = new User();
                    registers.setUsername(username);
                    registers.setEmail(email);
                    registers.setBorrow_num(0);
                    registers.setReserve_num(0);
                    registers.setIdentity(3);
                    if (!incomeService.addIncomeDeposit(registers)) {
                        message = "保证金写入记录失败";
                        res.put("statues", 403);
                        res.put("message", message);
                        return new ResponseEntity<>(res, HttpStatus.OK);
                    }
                    if (password == null) {
                        registers.setPassword("12345678");
                        accountService.save(registers);
                        message = "注册成功，初始密码是12345678";
                        res.put("statues", 200);
                    } else {
                        registers.setPassword(password);
                        accountService.save(registers);
                        message = "注册成功";
                        res.put("statues", 201);
                    }
                } else {
                    message = "用户名已被注册";
                    res.put("statues", 401);
                }
            } else {
                message = "用户名（手机号）或邮箱填写不符合规范";
                res.put("statues", 402);
            }
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
}
