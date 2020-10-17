package com.bbchan.library.controller;

import com.bbchan.library.entity.History;
import com.bbchan.library.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AllBookHistoryHandler {
    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping("/history")
    public ResponseEntity<Map<String, Object>> getdelhistory() {
        Map<String, Object> res = new HashMap<>();
        List<History> histories = historyRepository.findAll();

        String message;
        if (histories == null) {//无记录
            message = "无任何记录！";
            res.put("status", 400);
            res.put("message", message);
        } else {
            res.put("status", 200);
            res.put("LendHistoryList", histories);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
