package com.example.deliciouscard.repository;

import com.example.deliciouscard.entity.Post;
import com.example.deliciouscard.entity.PostLikes;
import com.example.deliciouscard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
    List<PostLikes> findAllByPost(Post post);

    PostLikes findByPostAndUser(Post post, User user);
}
