package ru.dictionary.botapi.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import ru.dictionary.cache.DataCache;
import ru.dictionary.collector.Parser;
import ru.dictionary.dao.BaseDiger;
import ru.dictionary.model.Data;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchHandler {

	DataCache dataCache;
	BaseDiger baseDiger;
	
	public String handleSearch(String messageText){
		Parser parser = new Parser();
		Data data = new Data();
		parser.getContent(data, messageText);
		dataCache.addAnswers(data.getAnswer());
		return data.getAnswer().get(0);
	}
	
	public String handleSearchInDic(String callBack){
		Parser parser = new Parser();
		Data data = baseDiger.getPlace(callBack);
		parser.getContent(data, dataCache.getRequest());
		return data.getAnswer().get(0);
	}
}
