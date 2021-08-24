package ru.dictionary.cache;

import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataCache {

	ArrayList<String> answer = null;
	int numberValue = 0;
	@Getter
	@Setter
	String request;
	
	public String getAnswer() {
		numberValue++;
		return answer.get(numberValue);
	}
	
	public void addAnswers(ArrayList<String> answer) {
		this.answer = answer;
	}
	
	public boolean isEmpty() {
		if(request == null) return true;
		return false;
	}
}
