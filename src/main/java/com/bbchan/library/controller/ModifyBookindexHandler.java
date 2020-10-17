package com.bbchan.library.controller;

import com.bbchan.library.entity.Book_detail;
import com.bbchan.library.entity.Book_index;
import com.bbchan.library.repository.Book_detailRepository;
import com.bbchan.library.repository.Book_indexRepository;
import com.bbchan.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ModifyBookindexHandler {
    @Autowired
    private BookService bookService;
    @Autowired
    private Book_indexRepository book_indexRepository;
    @Autowired
    private Book_detailRepository book_detailRepository;

    @PostMapping("/indexmodify")
    public ResponseEntity<Map<String, Object>> modifybookindex(@RequestBody Map params) {
        Map<String, Object> res = new HashMap<>();
        String bookid = (String) params.get("ISBN");
        System.out.println(bookid);
//        Book_detail book_detail= new Book_detail();
        Book_index book_index = book_indexRepository.findByBookid(bookid);

//        String newbookid = (String) params.get("newbookid");
//        book_index.setBookid(newbookid);
        book_index.setAuthor((String) params.get("author"));
//        book_index.setIamge((String) params.get("cover"));
        book_index.setPrice(Double.parseDouble((String) params.get("price")));
        book_index.setBookname((String) params.get("bookname"));
        book_index.setIntroduction((String) params.get("introduction"));
        System.out.println("heeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeere");
        List<Book_detail> book_detail = book_detailRepository.findAllByBookid(book_index.getBookid());
        for (Book_detail nowBookDetail : book_detail) {
            //            nowBookDetail.setBookid(book_index.getBookid());
//            nowBookDetail.setBook_detail_id(newbookid + "-" + i + 1);
            nowBookDetail.setAuthor(book_index.getAuthor());
            nowBookDetail.setPrice(book_index.getPrice());
            nowBookDetail.setBookname(book_index.getBookname());
            if (!bookService.modifybookdetail(nowBookDetail)) {
                String message = "detail同步失败！";
                res.put("statues", 400);
                res.put("message", message);
                return new ResponseEntity<>(res, HttpStatus.OK);
            }
//                bookService.saveBookDetail(nowBookDetail);
        }

        if (bookService.modifybookindex(book_index)) {
            String message = "index修改成功！";
            res.put("statues", 200);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } else {
            String message = "index修改失败！";
            res.put("statues", 401);
            res.put("message", message);
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }
}

//{"oldbookid":"999","newbookid":"996","author":"auindex","image":"image","price":33.9,"introduction":"intro"}