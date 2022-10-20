package com.example.bookstore.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity(name = BookVO.BOOK_TABLE)
@Table(name = BookVO.BOOK_TABLE)
@NoArgsConstructor
@AllArgsConstructor
public class BookVO implements Serializable {
	public static final int BOOK_SIZE = 10;
	public static final String BOOK_TABLE = "book";

	@Id
	@Expose
	@SerializedName(value = "id", alternate = { "cdBook" })
	@Column(name = "cdbook", nullable = false)
	private String cdBook;

	@Expose
	@SerializedName(value = "author", alternate = { "nmAuthor" })
	@Column(name = "nmauthor", nullable = false)
	private String nmAuthor;

	@Expose
	@SerializedName(value = "book", alternate = { "nmBook" })
	@Column(name = "nmbook", nullable = false)
	private String nmBook;

	@Expose
	@SerializedName(value = "description", alternate = { "deDescription" })
	@Column(name = "dedescription", nullable = false)
	private String deDescription;

	@Expose
	@SerializedName(value = "created_at", alternate = { "dtCreated" })
	@Column(name = "dtcreated", nullable = false)
	private Long dtCreated;
}
