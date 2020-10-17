package com.bbchan.library.controller;

import com.bbchan.library.entity.Book_index;
import com.bbchan.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class SearchBookHandler {
    @Autowired
    private BookService bookService;

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchBook(@RequestParam(value = "key") String key

    ) {
        Map<String, Object> res = new HashMap<>();
        String message;
//        int pagenum = Integer.parseInt(pageNum)-1;
//        int pagesize = Integer.parseInt(pageSize);
        List<Book_index> book_indexList = bookService.searchBook(key);
        List<Book_index> book_indexListAns = new ArrayList<>();
        int counts = book_indexList.size();
//        for(int i = 0; i < pagesize && pagesize * pagenum + i< counts; i++){
//            book_indexListAns.add(book_indexList.get(pagesize * pagenum + i));
//        }
        if (book_indexList.isEmpty()) {
            message = "无相关搜索结果！";
            res.put("status", 400);
            res.put("message", message);
        } else {
            res.put("statues", 200);
            res.put("counts", counts);
            res.put("book_indexList", book_indexList);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
