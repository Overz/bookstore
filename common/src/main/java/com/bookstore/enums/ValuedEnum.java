package com.bookstore.enums;

import java.io.Serializable;

public interface ValuedEnum<V> extends Serializable {
	V getValue();
}
