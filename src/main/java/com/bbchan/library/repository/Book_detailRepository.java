package com.bbchan.library.repository;

import com.bbchan.library.entity.Book_detail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Book_detailRepository extends JpaRepository<Book_detail, String> {
    Book_detail findByBookdetailid(String book_detail_id);

    List<Book_detail> findAllByBookid(String bookid);

    List<Book_detail> findAllByReserveusername(String username);
}
