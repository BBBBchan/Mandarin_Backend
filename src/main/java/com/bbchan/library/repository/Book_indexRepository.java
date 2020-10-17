package com.bbchan.library.repository;

import com.bbchan.library.entity.Book_index;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface Book_indexRepository extends JpaRepository<Book_index, String> {
    List<Book_index> findAllByBooknameLike(String name);

    Book_index findByBookid(String bookid);
}
