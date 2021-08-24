package ru.dictionary.botapi;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.Getter;
import ru.dictionary.botapi.handlers.HandlerKeyboardButton;
import ru.dictionary.botapi.shapers.ButtonsShaper;
import ru.dictionary.botapi.shapers.PossibleStates;
import ru.dictionary.botapi.shapers.TextShaper;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Dictionary extends TelegramLongPollingBot {

	@Getter
	String BotUsername;
	@Getter
	String BotToken;
	HandlerKeyboardButton handleKB;
	ButtonsShaper buttonsShaper;
	TextShaper textShaper;
	
	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		SendMessage genMessage = null;

		if(update.hasMessage()){
			genMessage = new SendMessage(String.valueOf(message.getChatId()), "");
			
			switch(message.getText()) {
				case "/help" -> textShaper.shapeTextMessage(genMessage, PossibleStates.HELP);
				default -> {
					textShaper.shapeTextAnswer(genMessage, message.getText());
					buttonsShaper.shapeAnswer(genMessage);
				}
			}
			sendMsg(genMessage);
		}	
		
		if(update.hasCallbackQuery()) {
			genMessage = new SendMessage(String.valueOf(update.getCallbackQuery().getMessage().getChatId()), "");
			handleKB.handleButton(update, genMessage);
			sendMsg(genMessage);
		}
	}

	private <T extends BotApiMethod> void sendMsg (T sendMessage) {
    		try {
            		execute(sendMessage);
        	} catch (TelegramApiException e) {
            		e.printStackTrace();
        	}
    	}
}
