package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository //описывает функциональность Note DAO
//унаследовал базовые CRUD-методы от CrudRepository 
public interface NoteRepository extends CrudRepository<Note, Integer>{
	
	//---------------1-й вариант--------------------------------
	//	SELECT * FROM note where title LIKE '%word1%' OR message like '%word2'
//	List<Note> findByTitleContainingOrMessageContaining(String word1, String word2);
	
	
	
	//---------------2-й вариант--------------------------------
	//SELECT * FROM note
	@Query("SELECT n FROM Note n where n.date=:date")//если не помним название таблиц в бд, берем названия классов
	List<Note> search(@Param("date")LocalDate date);
	
}
