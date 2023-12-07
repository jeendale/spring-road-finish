package com.example.springroadproject.repository;

import com.example.springroadproject.entity.PwHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PwRepository extends JpaRepository<PwHistory,Long> {
     List<PwHistory>findTop3ByUserIdOrderByCreatedAtDesc(Long id);
}
