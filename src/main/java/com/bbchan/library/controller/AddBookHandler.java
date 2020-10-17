package com.bbchan.library.controller;


import com.bbchan.library.entity.Book_category;
import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.entity.Book_index;
import com.bbchan.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AddBookHandler {
    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addbook(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        Book_detail book_detail = new Book_detail();
        book_detail.setAuthor((String) params.get("author"));
        book_detail.setBookid((String) params.get("ISBN"));
        book_detail.setLocation((String) params.get("location"));
        book_detail.setPrice(Double.parseDouble((String) params.get("price")));

        String introduction = (String) params.get("introduction");
//        book_detail.setIntroduction((String) params.get("introduction"));
        book_detail.setBookname((String) params.get("bookname"));
        int number = Integer.parseInt((String) params.get("number"));

        String url = (String) params.get("cover");
        System.out.println(url + "HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        String category = (String) params.get("category");
        Book_category book_category = bookService.addCategoryByBookid(book_detail.getBookid(), category);
        //根据书籍的详细信息，得到书籍的索引信息，如果没有函数会新建一个
        Book_index book_index = bookService.find(book_detail, url, introduction);

        //根据书籍数量，将书籍批量添加到其中
        int success_num = 0;
        for (int i = 0; i < number; i++) {
            if (bookService.addbook_detail(book_detail, book_index))
                success_num++;
        }
        String message;
        if (success_num != 0) {
            message = "成功添加" + success_num + "本书";
            res.put("statues", 200);
        } else {
            message = "fail";
            res.put("statues", 400);
        }
        res.put("message", message);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}

