package com.example.bookstore.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;

public class Constants {
	public static final String WORKDIR = new File("").getAbsolutePath();
	public static final Gson GSON = new GsonBuilder()
		.excludeFieldsWithoutExposeAnnotation()
		.setDateFormat("dd/MM/yyyy - HH:mm:ss - a")
		.create();
}
