package com.bbchan.library.controller;

import com.bbchan.library.entity.Library_income;
import com.bbchan.library.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class CheckincomeHandler {
    @Autowired
    private IncomeService incomeService;

    @PostMapping("/checkincome")
    public ResponseEntity<Map<String, Object>> CheckIncome(@RequestBody Map params) throws ParseException {
        Map<String, Object> res = new HashMap<>();//要返回的
        String message;
        String start_time = (String) params.get("start-time");//取参数开始时间
        String end_time = (String) params.get("end-time");
        //传入起始的时间，传入需要的时间
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date start_date = ft.parse(start_time);
        Date end_date = ft.parse(end_time);
        System.out.println(start_time + "xxxxx" + end_time);
        List<Library_income> income_list = incomeService.checkincome(start_date, end_date);
        double all_income = incomeService.getAllIncome(income_list);
        if (income_list.isEmpty()) {//无记录
            message = "无相关搜索结果！";
            res.put("status", 400);
            res.put("message", message);
        } else {
            res.put("status", 200);
            res.put("HistoryList", income_list);
            res.put("AllIncome", all_income);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
