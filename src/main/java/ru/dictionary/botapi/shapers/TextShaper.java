package ru.dictionary.botapi.shapers;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import ru.dictionary.botapi.handlers.SearchHandler;
import ru.dictionary.cache.DataCache;

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TextShaper {
	
	SearchHandler searchHandler;
	DataCache dataCache;
	
	public SendMessage shapeTextMessage(SendMessage sendMessage, PossibleStates state) {
		switch(state) {
			case CHOICE_DIC -> sendMessage.setText("Выберите словарь");
			case CHOICE_SEC -> sendMessage.setText("Выберите раздел для поиска");
			case HELP -> sendMessage.setText("bla bla");
		}
		return sendMessage;
	}
	
	public SendMessage shapeTextAnswer(SendMessage sendMessage, String messageText) {
		if(dataCache.isEmpty()) sendMessage.setText(searchHandler.handleSearch(messageText));
		else sendMessage.setText(searchHandler.handleSearchInDic(messageText));
		return sendMessage;
	}
}
