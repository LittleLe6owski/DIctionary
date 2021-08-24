package ru.dictionary.model;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Data {
	ArrayList<String> answer;
	String place;
	
	public Data (String place) {
		this.place = place;
	}
	
	public Data () {
		this.place = "search?term=";
	}
}
