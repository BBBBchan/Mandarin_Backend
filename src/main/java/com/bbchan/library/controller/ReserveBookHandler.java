package com.bbchan.library.controller;

import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.entity.Book_index;
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

import java.util.HashMap;
import java.util.Map;

@RestController
public class ReserveBookHandler {
    @Autowired
    private ReaderService readerService;
    @Autowired
    private BookService bookService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HistoryService historyService;

    @PostMapping("/reserve")
    public ResponseEntity<Map<String, Object>> reservebook(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String message;
        User user;
        String name = (String) params.get("reader_username");
        String ISBN = (String) params.get("ISBN");
        String book_detail_id = (String) params.get("book_detail_id");
//        Date date = new Date();
//        String Reserve_username = bookService.checkReserve_username(book_detail_id);
        user = accountService.findUser(name);
        //判断用户是否存在
        if (user == null) {
            message = "用户不存在，无法借书";
            res.put("statues", 400);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }

        Book_detail book_detail = bookService.findBook_detail(book_detail_id);
        Book_index book_index = bookService.onlyfindbook_index(book_detail.getBookid());
        //判断书籍是否存在
        if (book_index == null) {
            message = "书籍不存在，请输入正确的书籍编号";
            res.put("statues", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        System.out.println(book_detail);
        System.out.println(book_index);
        //判断书籍状态
        if (bookService.book_indexAvailable(book_index) && bookService.book_detailAvailable(book_detail)) {
            if (readerService.reservebook(user)) {
                if (bookService.Reservebook(book_index, book_detail, name)) {
                    message = "预约成功";
                    res.put("statues", 200);
                    res.put("message", message);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                } else {
                    message = "预约失败，数据库操作失败";
                    res.put("statues", 402);
                    res.put("message", message);
                    return new ResponseEntity<>(res, HttpStatus.OK);
                }
            } else {
                message = "预约数量已经到达上限";
                res.put("statues", 403);
                res.put("message", message);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
        } else {
            message = "您要预约的书已被占用，请预约其他书";
            res.put("statues", 404);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

}
