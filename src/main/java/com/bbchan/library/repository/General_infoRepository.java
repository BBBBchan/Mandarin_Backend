package com.bbchan.library.repository;

import com.bbchan.library.entity.General_info;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface General_infoRepository extends JpaRepository<General_info, Integer> {
    List<General_info> findAll();
}
