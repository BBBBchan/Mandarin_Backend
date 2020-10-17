package com.bbchan.library.repository;


import com.bbchan.library.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    History findByReaderusernameAndBookdetailidAndIsreturn(String username, String book_detail_id, boolean is_return);

    List<History> findAll();

    List<History> findAllByReaderusername(String username);

    List<History> findAllByBookdetailid(String book_detail_id);

    List<History> findAllByReaderusernameAndBookdetailid(String username, String book_detail_id);
}
