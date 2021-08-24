package ru.dictionary.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class Category extends Model{

	String section;
	String callback;

	@Override
	public String getGist() {
		return section;
	}
	
	@Override
	public String getCallBack() {
		return callback;
	}
}
