package com.example.bookstore.controllers;

import static com.example.bookstore.utils.Constants.GSON;

import com.example.bookstore.models.dto.BookDTO;
import com.example.bookstore.models.vo.BookVO;
import com.example.bookstore.service.interfaces.BookService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
	@Autowired
	BookService service;

	@GetMapping
	public Object getAll(
		@RequestParam(required = false, defaultValue = "0") int page,
		@RequestParam(required = false, defaultValue = "20") int pageSize,
		@RequestParam(required = false) String sort,
		BookDTO dto
	) {
		return service.listAll(page, pageSize, sort, dto.getBookVo());
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<String> getOne(@PathVariable String id) {
		return ResponseEntity.ok(GSON.toJson(new BookVO()));
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody BookDTO dto) {
		return ResponseEntity.status(201).body(GSON.toJson(service.save(dto.getBookVo())));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable String id) {
		return ResponseEntity.noContent().build();
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> update(
		@PathVariable String id,
		@RequestBody Optional<BookVO> book
	) {
		return ResponseEntity.noContent().build();
	}
}
