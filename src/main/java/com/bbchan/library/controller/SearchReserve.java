package com.bbchan.library.controller;

import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.repository.Book_detailRepository;
import com.bbchan.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchReserve {
    @Autowired
    private BookService bookService;
    @Autowired
    private Book_detailRepository bookDetailRepository;

    @GetMapping("/searchreserve")
    public ResponseEntity<Map<String, Object>> searchBook(@RequestParam(value = "username") String username
    ) {
        Map<String, Object> res = new HashMap<>();
        String message;
        List<Book_detail> book_detailList = bookDetailRepository.findAllByReserveusername(username);
        int counts = book_detailList.size();
        if (book_detailList.isEmpty()) {
            message = "无相关搜索结果！";
            res.put("status", 400);
            res.put("message", message);
        } else {
            res.put("statues", 200);
            res.put("counts", counts);
            res.put("book_detailList", book_detailList);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
