package com.example.bookstore.controllers;

import com.example.entities.BookVO;
import com.example.bookstore.service.interfaces.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.example.bookstore.utils.Constants.GSON;

@RestController
@RequestMapping(path = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookController {
	@Autowired
	BookService service;

	@GetMapping
	public Object getAll(
		@RequestParam(name = "page", required = false, defaultValue = "0") int page,
		@RequestParam(name = "pageSize", required = false, defaultValue = "20") int pageSize,
		@RequestParam(name = "sort", required = false) String sort,
		@RequestParam Map<String, String> query
	) {
		query.remove("page");
		query.remove("pageSize");
		query.remove("sort");
		return service.listAll(page, pageSize, sort, GSON.fromJson(query.toString(), BookVO.class));
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<String> getOne(@PathVariable String id) {
		return ResponseEntity.ok(GSON.toJson(service.findOne(id)));
	}

	@PostMapping
	public ResponseEntity<String> save(@RequestBody String json) {
		return ResponseEntity.status(201).body(GSON.toJson(service.save(GSON.fromJson(json, BookVO.class))));
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<Object> update(
		@PathVariable String id,
		@RequestBody String json
	) {
		BookVO vo = GSON.fromJson(json, BookVO.class);
		vo.setCdBook(id);
		service.update(vo);
		return ResponseEntity.noContent().build();
	}
}
