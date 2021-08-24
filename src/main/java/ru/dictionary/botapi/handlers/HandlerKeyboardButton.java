package ru.dictionary.botapi.handlers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import ru.dictionary.botapi.shapers.ButtonsShaper;
import ru.dictionary.botapi.shapers.PossibleStates;
import ru.dictionary.botapi.shapers.TextShaper;
import ru.dictionary.cache.DataCache;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HandlerKeyboardButton{

	ButtonsShaper buttonsShaper;
	DataCache dataCache;
	TextShaper textShaper;
	
	public void handleButton (Update update, SendMessage genMessage) {
		
		String callBack = update.getCallbackQuery().getData();
		if(update.getMessage().getText() == null)
		System.out.println("Ok");
		
		switch(callBack) {
			case "InOtherDictionary" -> {
				textShaper.shapeTextMessage(genMessage, PossibleStates.CHOICE_SEC);
				buttonsShaper.shapeChoiseSection(genMessage);
			}
			case "OtherValue" -> {
				genMessage.setText(dataCache.getAnswer());
				buttonsShaper.shapeAnswer(genMessage);
			}
			default -> choiceDic(genMessage,callBack);
		}
	}
	
	private void choiceDic(SendMessage genMessage, String callBack) {
		if(callBack.startsWith("Cat")) {
			buttonsShaper.shapeChoiseDictionary(genMessage, callBack);
			textShaper.shapeTextMessage(genMessage, PossibleStates.CHOICE_DIC);
		}
		if(callBack.startsWith("Dic")) {
			textShaper.shapeTextAnswer(genMessage, callBack);
		}
	}
}
