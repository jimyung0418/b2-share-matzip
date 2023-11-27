package com.example.deliciouscard.repository;

import com.example.deliciouscard.entity.Comment;
import com.example.deliciouscard.entity.CommentLikes;
import com.example.deliciouscard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentLikesRepository extends JpaRepository<CommentLikes,Long> {

    List<CommentLikes> findAllByComment(Comment comment);

    CommentLikes findByCommentAndUser(Comment comment, User user);
}
