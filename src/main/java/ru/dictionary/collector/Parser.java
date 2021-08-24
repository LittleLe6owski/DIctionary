package ru.dictionary.collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ru.dictionary.model.Data;

public class Parser {
	
	public void getContent(Data data, String request) {

		Document doc = null;

		try {
			doc = Jsoup.connect("https://gufo.me/" + data.getPlace() + request).userAgent("google").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element id ;
		if (data.getPlace().equals("search?term=")) {
			id = doc.getElementById("dictionary-search");
		} else id = doc.getElementById("dictionary-acticle");

		Elements value = id.getElementsByTag("p");
		data.setAnswer(filter(value));
	}
	
	private ArrayList<String> filter (Elements value) {
		
		ArrayList<String> list_value = new ArrayList<>(value.size());
		
		for(Element i: value) {
			Pattern pat = Pattern.compile("<.+?>");
			Matcher mat = pat.matcher(i.toString());
			list_value.add(mat.replaceAll(""));
		}
		return list_value;
	}
}