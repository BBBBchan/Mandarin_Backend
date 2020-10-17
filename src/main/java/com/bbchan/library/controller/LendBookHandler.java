package com.bbchan.library.controller;

import com.bbchan.library.entity.History;
import com.bbchan.library.entity.User;
import com.bbchan.library.service.AccountService;
import com.bbchan.library.service.BookService;
import com.bbchan.library.service.HistoryService;
import com.bbchan.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LendBookHandler {
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookService bookService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HistoryService historyService;

    @PostMapping("/lend")
    public ResponseEntity<Map<String, Object>> lendbook(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String message;
        User user;
        String name = (String) params.get("reader_username");
        String ISBN = (String) params.get("ISBN");
        String book_detail_id = (String) params.get("book_detail_id");
        String librarian = (String) params.get("libarian_username");        //添加历史消息功能
        Date date = new Date();
        String Reserve_username = bookService.checkReserve_username(book_detail_id);
        user = accountService.findUser(name);
        if (user == null) {
            message = "用户不存在，无法借书";
            res.put("statues", 400);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        Date Reserve_time = bookService.checkReserve_time(book_detail_id);
        //判断借书数量不能能超过3本
        if (user.getUsername().equals(Reserve_username) && Reserve_time != null) {
            if (user.getBorrow_num() == null) user.setBorrow_num(0);
            else if (user.getBorrow_num() > 3) {
                message = "借书达到上限，请先还书后再借书";
                res.put("statues", 401);
                res.put("message", message);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            double time = (double) (-Reserve_time.getTime() + date.getTime()) / (1000 * 60 * 60);
            System.out.println(Reserve_time.getTime());
            System.out.println(date.getTime());
            System.out.println(time);
            if (time <= 2) {
                if (readerService.lendbook(user, ISBN, book_detail_id)) {
                    History history = historyService.newHistory(user.getUsername(), book_detail_id, librarian);
                    if (historyService.addlendHistory(history)) {
                        //return "借书成功";
                        message = "借书成功";
                        res.put("statues", 200);
                    } else {
                        message = "借书失败，添加历史记录失败";
                        res.put("statues", 406);
                    }
                } else {
                    message = "借书失败，数据库操作出错";
                    res.put("statues", 402);
                }
            } else {
                message = "该用户预约已超时，请重新预约";
                res.put("statues", 403);
            }
        } else {
            message = "借书失败，用户未预约该本图书";
            res.put("statues", 405);

        }
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

}
