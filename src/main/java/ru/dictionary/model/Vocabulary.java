package ru.dictionary.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vocabulary extends Model{
	
	String name;
	String callback;

	@Override
	public String getGist() {
		return name;
	}
	
	@Override
	public String getCallBack() {
		return callback;
	}
}
