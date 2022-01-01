package com.example.bookstore.service.interfaces;

import com.example.entities.BookVO;

public interface BookService {
	Object listAll(int page, int pageSize, String sort, BookVO vo);

	BookVO findOne(String id);

	BookVO save(BookVO b);

	void delete(String id);

	void update(BookVO vo);
}
