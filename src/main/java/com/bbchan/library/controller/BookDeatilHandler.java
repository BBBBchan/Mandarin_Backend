package com.bbchan.library.controller;

import com.bbchan.library.entity.Book_category;
import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.entity.Book_index;
import com.bbchan.library.service.BookService;
import com.bbchan.library.service.CategoryService;
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
public class BookDeatilHandler {
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/detailinfo")
    public ResponseEntity<Map<String, Object>> searchBook(@RequestParam(value = "bookid") String bookid) {
        Map<String, Object> res = new HashMap<>();
        String message;
        Book_index book_index = bookService.onlyfindbook_index(bookid);
        if (book_index == null) {
            message = "图书不存在";
            res.put("status", 400);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
//        List<Book_category> book_categoryList = bookService.findAllCategoryByBookId(bookid);
        Book_category book_category = bookService.findCategoryByBookId(bookid);
        List<Book_detail> book_detailList = bookService.findAllDetailByBookId(bookid);
        if (book_detailList.isEmpty()) {
            message = "书籍详细信息不存在";
            res.put("status", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        res.put("status", 200);
        res.put("book_index", book_index);
        res.put("book_categoryList", book_category);
        res.put("book_detailList", book_detailList);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
