package com.example.deliciouscard.repository;


import com.example.deliciouscard.entity.Guestbook;
import com.example.deliciouscard.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {

 List<Guestbook> findAllByUser(User user);
}
