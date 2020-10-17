package com.bbchan.library.controller;

import com.bbchan.library.entity.General_info;
import com.bbchan.library.repository.General_infoRepository;
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
public class GeneralInfoHandler {
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private General_infoRepository generalInfoRepository;

    @GetMapping("/generalinfo")
    public ResponseEntity<Map<String, Object>> getgeneralinfo() {
        Map<String, Object> res = new HashMap<>();
        List<General_info> general_infoList = generalInfoRepository.findAll();
        String message;
        if (general_infoList.isEmpty()) {//无记录
            message = "无任何记录！";
            res.put("status", 400);
            res.put("message", message);
        } else {
            res.put("status", 200);
            res.put("GeneralInfo", general_infoList.get(0));
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
