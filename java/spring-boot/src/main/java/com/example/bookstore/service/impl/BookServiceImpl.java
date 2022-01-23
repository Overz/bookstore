package com.example.bookstore.service.impl;

import com.example.entities.BookVO;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.service.interfaces.BookService;
import com.example.bookstore.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.bookstore.utils.Nanoid.nanoid;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository repo;

	@Override
	public Object listAll(int page, int pageSize, String sort, BookVO vo) {
		Pagination p = Pagination.buildPagination(page, pageSize, sort, vo);
		return p.execute(repo).toDTO();
	}

	@Override
	public BookVO findOne(String id) {
		return repo.findById(id).orElse(new BookVO());
	}

	@Override
	public BookVO save(BookVO b) {
		b.setCdBook(nanoid(BookVO.BOOK_SIZE));
		b.setDtCreated(System.currentTimeMillis());
		return repo.save(b);
	}

	@Override
	public void delete(String id) {
		repo.deleteById(id);
	}

	@Override
	public void update(BookVO vo) {
	}
}
