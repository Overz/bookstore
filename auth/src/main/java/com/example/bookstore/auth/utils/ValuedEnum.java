package com.example.bookstore.auth.utils;

import java.io.Serializable;

public interface ValuedEnum<V> extends Serializable {
	V getValue();
}
