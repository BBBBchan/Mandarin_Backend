package com.bbchan.library.controller;

import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.repository.Book_detailRepository;
import com.bbchan.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ModifyBookdetailHandler {
    @Autowired
    private BookService bookService;
    @Autowired
    private Book_detailRepository book_detailRepository;

    @PostMapping("/detailmodify")
    public ResponseEntity<Map<String, Object>> modifybookdetail(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String book_detail_id = (String) params.get("book_detail_id");
        Book_detail book_detail = book_detailRepository.findByBookdetailid(book_detail_id);
        System.out.println("hreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//        book_detail.setBookname((String) params.get("bookname"));
//        book_detail.setAuthor((String) params.get("author"));
        book_detail.setLocation((String) params.get("location"));
//        book_detail.setPrice((Double) params.get("price"));
//        book_detail.setIntroduction((String) params.get("introduction"));


        if (bookService.modifybookdetail(book_detail)) {
            String message = "detail修改成功！";
            res.put("statues", 200);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            String message = "detail修改失败！";
            res.put("statues", 400);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
}


//测试数据：{"book_detail_id":"999-1","bookname":"aaa","author":"au","location":"asdd","price":33.4,"introduction":"errt"}