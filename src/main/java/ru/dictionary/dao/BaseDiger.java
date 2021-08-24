package ru.dictionary.dao;

import java.util.List;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import ru.dictionary.model.Category;
import ru.dictionary.model.Data;
import ru.dictionary.model.Vocabulary;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BaseDiger {

	JdbcTemplate jdbcTemplate;

	public Data getPlace(String callBack) {
		return jdbcTemplate.query
					("SELECT place FROM address"
					+ "JOIN backnames ON(address.backnames_id = backnames_id)"
					+ "WHERE backnames.callback = ?",
						new BeanPropertyRowMapper<>(Data.class), callBack)
						.stream().findAny().orElse(null);
	}
	
	public List <Category> getAllSections() {
		return jdbcTemplate.query
				("SELECT section, callback FROM category"
				+ "JOIN backnames ON(category.backnames_id = backnames.id)",
						new BeanPropertyRowMapper<>(Category.class));
	}
	
	public List <Vocabulary> getDicOnSec(String section) {
		return jdbcTemplate.query
						("SELECT name, callback FROM address"
						+ "JOIN backnames ON(address.backnames_id = backnames.id)"
						+ "JOIN category ON(category.id = address.category_id)"
						+ "WHERE category.section = (SELECT section FROM category"
						+ "JOIN backnames ON(category.backnames_id = backnames.id)"
						+ "WHERE backnames.callback = ?)",
								new BeanPropertyRowMapper<>(Vocabulary.class), section);
	}
}
