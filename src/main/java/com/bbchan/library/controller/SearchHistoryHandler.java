package com.bbchan.library.controller;

import com.bbchan.library.entity.History;
import com.bbchan.library.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RequestMapping("/reader")
public class SearchHistoryHandler {
    @Autowired
    private HistoryService historyService;

    @GetMapping("/history/username")
    public ResponseEntity<Map<String, Object>> Historyview(@RequestParam(value = "id") String username) {
        Map<String, Object> res = new HashMap<>();//要返回的
        String message;
        List<History> historyList = historyService.findByUsername(username);  //取出historylist
        if (historyList.isEmpty()) {//无记录
            message = "无相关搜索结果！";
            res.put("status", 200);
            res.put("message", message);
        } else {
            res.put("status", 200);
            res.put("HistoryList", historyList);
//            System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}