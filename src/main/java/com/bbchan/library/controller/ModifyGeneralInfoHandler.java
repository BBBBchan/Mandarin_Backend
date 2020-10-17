package com.bbchan.library.controller;

import com.bbchan.library.entity.General_info;
import com.bbchan.library.repository.General_infoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ModifyGeneralInfoHandler {
    @Autowired
    private General_infoRepository general_infoRepository;

    @PostMapping("/modifygeneralinfo")
    public ResponseEntity<Map<String, Object>> modifygeneralinfo(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String message;
        List<General_info> general_infoList = general_infoRepository.findAll();
        General_info general_info = general_infoList.get(0);
        general_info.setFine_value((double) Float.parseFloat((String) params.get("fine_value")));
        general_info.setReturn_period(Integer.parseInt((String) params.get("return_period")));
        general_info.setSecurity_deposit((double) Float.parseFloat((String) params.get("security_deposit")));

        General_info result = general_infoRepository.save(general_info);
        message = "修改成功";
        res.put("statues", 200);
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
