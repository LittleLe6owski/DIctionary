package ru.dictionary.botapi;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum Buttons {
	OTHER_VALUE("Другое значение","OtherValue"),
	IN_OTHER_DICTIONARY("В другом словаре","InOtherDictionary");
	
	String text;
	String callback;
	
	Buttons(String text, String callback){
		this.text = text;
		this.callback = callback;
	}
}
