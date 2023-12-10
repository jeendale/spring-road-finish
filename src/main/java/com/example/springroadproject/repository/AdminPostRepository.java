package com.example.springroadproject.repository;

import com.example.springroadproject.entity.AdminPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminPostRepository extends JpaRepository<AdminPost,Long> {
}
