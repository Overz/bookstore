package com.example.bookstore.utils;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.lang.reflect.Field;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pagination implements Pageable, Serializable {
	private int page;
	private int pageSize;
	private Long totalRecords;
	private Object data;
	private Sort sort;

	// SELECT * FROM book b WHERE b.cdBook = '1' LIMIT 20	OFFSET 1

	@Override
	public int getPageNumber() {
		return this.page / this.pageSize;
	}

	@Override
	public int getPageSize() {
		return this.pageSize;
	}

	@Override
	public long getOffset() {
		return this.page;
	}

	@Override
	public Sort getSort() {
		return this.sort;
	}

	@Override
	public Pageable next() {
		return Pagination
			.builder()
			.page(getPage() + getPageSize())
			.pageSize(getPageSize())
			.totalRecords(getTotalRecords())
			.sort(getSort())
			.data(getData())
			.totalRecords(getTotalRecords())
			.build();
	}

	public Pageable previous() {
		return hasPrevious()
			? Pagination
			.builder()
			.page(getPage() - getPageSize())
			.pageSize(getPageSize())
			.sort(getSort())
			.data(getData())
			.totalRecords(getTotalRecords())
			.build()
			: this;
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : first();
	}

	@Override
	public Pageable first() {
		return Pagination
			.builder()
			.page(0)
			.pageSize(getPageSize())
			.totalRecords(getTotalRecords())
			.data(getData())
			.sort(getSort())
			.build();
	}

	@Override
	public Pageable withPage(int pageNumber) {
		return Pagination
			.builder()
			.page(pageNumber)
			.pageSize(getPageSize())
			.totalRecords(getTotalRecords())
			.data(getData())
			.sort(getSort())
			.build();
	}

	@Override
	public boolean hasPrevious() {
		return this.page > pageSize;
	}

	public static Pagination buildPagination(int page, int pageSize, String sort, Object o) {
		String[] content = sort.split(",");
		Sort.Direction direction = Sort.Direction.fromString(content[1]);

		Pagination.PaginationBuilder builder = Pagination
			.builder()
			.page(page)
			.pageSize(pageSize)
			.data(o);

		Field[] fields = o.getClass().getDeclaredFields();
		for (Field field : fields) {
			SerializedName serial = field.getAnnotation(SerializedName.class);
			if (serial != null) {
				if (serial.value().equalsIgnoreCase(content[0])) {
					builder.sort(Sort.by(direction, serial.alternate()[0]));
					break;
				}
			}
		}

		return builder.build();
	}
}
