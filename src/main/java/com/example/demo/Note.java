package com.example.demo;



import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//геттры, сеттеры, констукторы, equals, hashcode, toString
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Note {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String message;
	private LocalDate date;

}
