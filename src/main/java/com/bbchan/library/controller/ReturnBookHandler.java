package com.bbchan.library.controller;

import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.entity.Book_index;
import com.bbchan.library.entity.History;
import com.bbchan.library.entity.User;
import com.bbchan.library.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReturnBookHandler {
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookService bookService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private IncomeService incomeService;

    @PostMapping("/return")
    public ResponseEntity<Map<String, Object>> retrunbook(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String message;
        User user;
        String name = (String) params.get("reader_username");
        String ISBN = (String) params.get("ISBN");
        String book_detail_id = (String) params.get("book_detail_id");
        String librarian = (String) params.get("libarian_username");
        //查找用户
        user = accountService.findUser(name);
        if (user == null) {
            message = "用户不存在，无法还书";
            res.put("statues", 400);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        //查找到书籍，书种，历史记录
        Book_index book_index = bookService.onlyfindbook_index(ISBN);
        Book_detail book_detail = bookService.findBook_detail(book_detail_id);
        if (book_detail == null || book_index == null) {
            message = "书籍不存在，请输入正确的书籍编号";
            res.put("statues", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        History history = historyService.findHistory(name, book_detail_id);
        if (history == null) {
            message = "借阅记录不存在";
            res.put("statues", 402);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        //修改书籍的状态，书种的被借阅数
        if (bookService.returnbook(book_index, book_detail)) {

            //更新历史了记录，判断是否超期
            if (historyService.flushHistory(history, true)) {
                if (incomeService.addincomeBook(history)) {
                    //修改用户的借阅数量
                    if (readerService.returnbook(user)) {

                        message = "还书成功";
                        res.put("statues", 200);
                        res.put("message", message);
                        return new ResponseEntity<>(res, HttpStatus.OK);
                    } else {
                        message = "用户数据修改失败";
                        res.put("statues", 405);
                        res.put("message", message);
                        return new ResponseEntity<>(res, HttpStatus.OK);
                    }
                } else {
                    message = "还款失败";
                    res.put("statues", 406);
                    res.put("message", message);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                }
            } else {
                message = "历史数据修改失败";
                res.put("statues", 404);
                res.put("message", message);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        } else {
            message = "数据库修改失败";
            res.put("statues", 403);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

}