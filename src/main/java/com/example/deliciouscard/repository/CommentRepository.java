package com.example.deliciouscard.repository;

import com.example.deliciouscard.entity.Comment;
import com.example.deliciouscard.entity.CommentLikes;
import com.example.deliciouscard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
