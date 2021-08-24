package ru.dictionary.botapi.shapers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import ru.dictionary.botapi.Buttons;
import ru.dictionary.dao.BaseDiger;
import ru.dictionary.model.Category;
import ru.dictionary.model.Model;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ButtonsShaper {
	
	BaseDiger baseDiger;
			
	public void shapeAnswer(SendMessage sendMessage) {		
		genListButtons(sendMessage,
				Arrays.asList(new Category(Buttons.OTHER_VALUE.getText(), Buttons.OTHER_VALUE.getCallback()),
						new Category(Buttons.IN_OTHER_DICTIONARY.getText(),Buttons.IN_OTHER_DICTIONARY.getCallback())));
	}
	
	public void shapeChoiseSection(SendMessage sendMessage) {
		genListButtons(sendMessage, baseDiger.getAllSections());
	}
	
	public void shapeChoiseDictionary(SendMessage sendMessage, String category) {
		genListButtons(sendMessage, baseDiger.getDicOnSec(category));
	}
	
	private <T extends Model> void genListButtons(SendMessage message, List<T> allSections) {	

		List<List<InlineKeyboardButton>> columnList = new ArrayList<>();
		for(int i = 0; i < allSections.size()-1; i+=2) {
			columnList.add(Arrays.asList(genButton(allSections.get(i)),genButton(allSections.get(i+1))));
		}
		if(allSections.size() % 2 == 1) {
			columnList.add(Arrays.asList(genButton(allSections.get(allSections.size()-1))));
		}
		message.setReplyMarkup(new InlineKeyboardMarkup(columnList));
	}
	
	private <T extends Model> InlineKeyboardButton genButton(T model) {
		InlineKeyboardButton ikm = new InlineKeyboardButton(model.getGist());
		ikm.setCallbackData(model.getCallBack());
		return ikm;
	}	
}
