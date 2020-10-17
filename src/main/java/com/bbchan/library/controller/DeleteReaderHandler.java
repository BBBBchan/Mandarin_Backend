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
public class DeleteReaderHandler {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/deletereader")
    public ResponseEntity<Map<String, Object>> deletereader(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String reader_username = (String) params.get("reader_username");
        String librarian_username = (String) params.get("librarian_username");
        User librarianuser = userRepository.findByUsername(librarian_username);
        User readeruser = userRepository.findByUsername(reader_username);
        String message;
        if (librarianuser.getIdentity() != 2) {
            message = "非管理员无权限进行操作！";
            res.put("status", 400);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else if (readeruser.getIdentity() != 3) {
            message = "被操作者非普通用户，无法进行操作！";
            res.put("status", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            if (readeruser.getBorrow_num() != 0 || readeruser.getReserve_num() != 0) {
                message = "该用户存在借书或预约，无法进行此操作！";
                res.put("status", 402);
            } else {
//                userRepository.deleteByUsername(reader_username);
                userRepository.delete(readeruser);
                message = reader_username + "用户删除成功！" + "操作者管理员：" + librarian_username;
                res.put("statues", 200);
            }
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
}
