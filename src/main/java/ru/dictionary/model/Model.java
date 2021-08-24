package ru.dictionary.model;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Model {
	
	String callBack;
	
	public abstract String getCallBack();
	public abstract String getGist();
}
