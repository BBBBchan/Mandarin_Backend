package com.bbchan.library.controller;

import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.entity.Book_index;
import com.bbchan.library.entity.Delete_history;
import com.bbchan.library.entity.User;
import com.bbchan.library.repository.Book_detailRepository;
import com.bbchan.library.repository.Book_indexRepository;
import com.bbchan.library.service.AccountService;
import com.bbchan.library.service.BookService;
import com.bbchan.library.service.HistoryService;
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
public class DelBookHandler {
    @Autowired
    private BookService bookService;
    @Autowired
    private Book_detailRepository book_detailRepository;
    @Autowired
    private Book_indexRepository book_indexRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private HistoryService historyService;

    @PostMapping("/deletebookdetail")
    public ResponseEntity<Map<String, Object>> deletebookdetail(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String book_detail_id = (String) params.get("book_detail_id");
        Book_detail book_detail = book_detailRepository.findByBookdetailid(book_detail_id);
        String message;
        if (book_detail.getStatus() != 0) {
            message = "此图书无法进行此操作！";
            res.put("statues", 400);
        } else {
            String librarian_username = (String) params.get("username");
            if (librarian_username == null) {
                librarian_username = "13329198777";
            }
            System.out.println(librarian_username);
            User user = accountService.findUser(librarian_username);
            if (user == null || user.getIdentity() != 2) {
                message = "用户不存在，无法进行操作！";
                res.put("statues", 401);
                res.put("message", message);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
            String bookid = book_detail.getBookid();
            String bookname = book_detail.getBookname();
            book_detailRepository.deleteById(book_detail_id);

            Book_index book_index = book_indexRepository.findByBookid(bookid);
            book_index.setTotal_num(book_index.getTotal_num() - 1);
            Date date = new Date();
            date.setTime(date.getTime());
            Delete_history delete_history = new Delete_history();
            delete_history.setBook_detail_id(book_detail_id);
            delete_history.setDelete_time(date);
            delete_history.setId(null);
            delete_history.setBookname(bookname);


            delete_history.setUsername(librarian_username);

            if (bookService.modifybookindex(book_index) && historyService.savedelhistory(delete_history)) {
                message = book_detail_id + "号图书删除成功！";
                res.put("statues", 200);
            } else {
                message = book_detail_id + "号图书删除失败！";
                res.put("statues", 402);
            }
        }
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
