package com.bbchan.library.repository;

import com.bbchan.library.entity.Book_category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Book_categoryRepository extends JpaRepository<Book_category, Integer> {
    List<Book_category> findAllByBookid(String Bookid);

    Book_category findByBookid(String ISBN);

    //List<Book_category> findAllByBooknameLike(String name);
    List<Book_category> findAllByCategoryLike(String category);
}

