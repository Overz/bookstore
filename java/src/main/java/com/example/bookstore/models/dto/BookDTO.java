package com.example.bookstore.models.dto;

import com.example.bookstore.models.vo.BookVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.builder.ToStringStyle;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
	@Expose
	@SerializedName("id")
	private String id;

	@Expose
	@SerializedName("author")
	private String author;

	@Expose
	@SerializedName("book")
	private String book;

	@Expose
	@SerializedName("description")
	private String description;

	@Expose
	@SerializedName("created_at")
	private Long created_at;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public BookVO getBookVo() {
		return BookVO
			.builder()
			.cdBook(id)
			.nmBook(book)
			.nmAuthor(author)
			.deDescription(description)
			.dtCreated(created_at)
			.build();
	}
}
