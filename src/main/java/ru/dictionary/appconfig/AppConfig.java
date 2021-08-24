package ru.dictionary.appconfig;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import ru.dictionary.botapi.Dictionary;
import ru.dictionary.botapi.handlers.HandlerKeyboardButton;
import ru.dictionary.botapi.handlers.SearchHandler;
import ru.dictionary.botapi.shapers.ButtonsShaper;
import ru.dictionary.botapi.shapers.TextShaper;
import ru.dictionary.cache.DataCache;
import ru.dictionary.dao.BaseDiger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@ComponentScan("ru.dictionary")
@PropertySource("classpath:application.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppConfig {
	    
	@Value("${telegrambot.userName}")
	String botUserName;
		
	@Value("${telegrambot.botToken}")
	String botToken;
	
	@Value("${spring.data.postgresql.driverclassname}")
	String driverClassname;
	
	@Value("${spring.data.postgresql.url}")
	String databaseUrl;
	
	@Value("${spring.data.postgresql.username}")
	String databaseUsername;
	
	@Value("${spring.data.postgresql.password}")
	String databasePassword;
	
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName(driverClassname);
		dataSource.setUrl(databaseUrl);
		dataSource.setUsername(databaseUsername);
		dataSource.setPassword(databasePassword);
		
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource());
	} 	
	
	@Bean
	public BaseDiger baseDiger() {
		return new BaseDiger(jdbcTemplate());
	}
	
	@Bean
	public ButtonsShaper buttonShaper() {
		return new ButtonsShaper(baseDiger());
	}
	
	@Bean
	public  SearchHandler searchHandler() {
		return new  SearchHandler(dataCache(), baseDiger());
	}
	
	@Bean
	public TextShaper textShaper() {
		return new TextShaper(searchHandler(), dataCache());
	}
	
	@Bean
	public DataCache dataCache() {
		return new DataCache();
	}
	    
	@Bean
	public HandlerKeyboardButton handleKB() {
		return new HandlerKeyboardButton(buttonShaper(), dataCache(), textShaper());
	}
		
	@Bean
	public void MySuperTelegramBot(){
		try{
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new Dictionary(botUserName, botToken, handleKB(), buttonShaper(), textShaper()));
		}catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}
	