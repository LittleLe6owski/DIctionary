package ru.dictionary;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.dictionary.appconfig.AppConfig;

public class Start {
	public static void main(String args[]) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                AppConfig.class);
                
        context.getBean("appConfig", AppConfig.class);
        context.close();
	}
}
