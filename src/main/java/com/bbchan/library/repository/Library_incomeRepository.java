package com.bbchan.library.repository;

import com.bbchan.library.entity.Library_income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Library_incomeRepository extends JpaRepository<Library_income, String> {
    List<Library_income> findAll();
}
