package com.example.bookstore.service.impl;

import com.example.bookstore.models.vo.BookVO;
import com.example.bookstore.repositories.BookRepository;
import com.example.bookstore.service.interfaces.BookService;
import com.example.bookstore.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.example.bookstore.utils.Nanoid.nanoid;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookRepository repo;

	@Override
	public Object listAll(int page, int pageSize, String sort, BookVO vo) {
		Pagination p = Pagination.buildPagination(page, pageSize, sort, vo);
		Example<BookVO> e = Example.of(vo);

		var find = repo.findAll(e, p);
		p.setData(find.get().collect(Collectors.toList()));
		p.setTotalRecords(find.getTotalElements());
		return find;
	}

	@Override
	public BookVO save(BookVO b) {
		b.setCdBook(nanoid(BookVO.BOOK_SIZE));
		b.setDtCreated(System.currentTimeMillis());
		return repo.save(b);
	}
}
