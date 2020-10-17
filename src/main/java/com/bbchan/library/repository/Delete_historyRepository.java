package com.bbchan.library.repository;

import com.bbchan.library.entity.Delete_history;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Delete_historyRepository extends JpaRepository<Delete_history, Integer> {
    List<Delete_history> findAll();

    List<Delete_history> findAllByUsername(String username);

    List<Delete_history> findAllByBooknameLike(String bookname);
}
