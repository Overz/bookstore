package com.example.bookstore.repositories;

import com.example.bookstore.entities.BookVO;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface BookRepository extends JpaRepository<BookVO, String> {}
