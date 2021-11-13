package com.example.bookstore.service.interfaces;

import com.example.bookstore.models.vo.BookVO;

public interface BookService {
	Object listAll(int page, int pageSize, String sort, BookVO vo);

	BookVO save(BookVO b);
}
