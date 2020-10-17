package com.bbchan.library.controller;

import com.bbchan.library.entity.Delete_history;
import com.bbchan.library.repository.Delete_historyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AllDelHistoryHandler {
    @Autowired
    private Delete_historyRepository delete_historyRepository;

    @GetMapping("/delhistory")
    public ResponseEntity<Map<String, Object>> getdelhistory() {
        Map<String, Object> res = new HashMap<>();
        List<Delete_history> delete_histories = delete_historyRepository.findAll();
        String message;
        if (delete_histories == null) {//无记录
            message = "无任何记录！";
            res.put("status", 400);
            res.put("message", message);
        } else {
            res.put("status", 200);
            res.put("DelHistoryList", delete_histories);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
