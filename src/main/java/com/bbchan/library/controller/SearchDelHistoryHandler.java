package com.bbchan.library.controller;

import com.bbchan.library.entity.Delete_history;
import com.bbchan.library.repository.Delete_historyRepository;
import com.bbchan.library.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchDelHistoryHandler {
    @Autowired
    private Delete_historyRepository delete_historyRepository;
    @Autowired
    private AccountService accountService;

    @GetMapping("/delhistory/username")
    public ResponseEntity<Map<String, Object>> getdelhistory(@RequestParam(value = "id") String username) {
        Map<String, Object> res = new HashMap<>();
        String message;
        List<Delete_history> delete_histories = delete_historyRepository.findAllByUsername(username);
        if (delete_histories.isEmpty()) {//无记录
            message = "无任何搜索记录！";
            res.put("status", 400);
            res.put("message", message);
        } else {
            res.put("status", 200);
            res.put("DelHistoryList", delete_histories);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/delhistory/bookname")
    public ResponseEntity<Map<String, Object>> getdelhistorybybookname(@RequestParam(value = "id") String bookname) {
        Map<String, Object> res = new HashMap<>();
        String message;
        List<Delete_history> delete_histories = delete_historyRepository.findAllByBooknameLike("%" + bookname + "%");
        res.put("status", 200);
        res.put("DelHistoryList", delete_histories);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
