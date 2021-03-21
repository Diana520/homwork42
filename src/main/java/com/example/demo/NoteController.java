package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/notes")
public class NoteController {
	//private List<Note> notes = new ArrayList<>();
	private NoteRepository noteRepository;
	
	
	@Autowired
	public NoteController(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	@GetMapping("/create")
	public String create() {
		return "create_note";
	}


	@PostMapping("/add")
	public String add(@ModelAttribute(name = "note") Note note) {
		note.setDate(note.getDate().now());
		noteRepository.save(note);
		return "redirect:/notes";
	}
	
/*	@PostMapping("/add")
	public String addNote(@ModelAttribute(name="note") Note note) {
//		int id=notes.stream().map(n->n.getId())//получение идентификатора
//				.max((i1,i2)->i1-i2)//нахождение максимального элемента айди, если он есть
//				.orElse(0);//если пусто, то 0
//		note.setId(++id);
//		notes.add(note);
		noteRepository.save(note);
		return "redirect:/notes";
	}*/
	
	@GetMapping
	public String all(Model model)
	{
		//model.addAttribute("notes",notes);
		model.addAttribute("notes",noteRepository.findAll());
		return "notes";
	}
	
	@GetMapping("/{id}")
	public String info(@PathVariable(name="id")int id, Model model) {
		Note note = noteRepository.findById(id).get();
//		Note note = notes.stream().filter(n->n.getId()==id).findFirst().get();
    	model.addAttribute("note",note);
		return "info";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteNote(@PathVariable(name="id")int id) {
		noteRepository.deleteById(id);
		return "redirect:/notes";
	}
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable(name="id")int id, Model model)
	{
		
		model.addAttribute("note",noteRepository.findById(id).get());
		//System.out.println(noteRepository.findById(id).get());
		return "edit_note";
	}
	
	@PostMapping("/change")
	public String change(@ModelAttribute(name="note")Note note)
	{
		List<Note> notes = (List<Note>) noteRepository.findAll();
		for(Note i:notes)
		{
			if(i.getId()==note.getId())
			{
				note.setDate(note.getDate().now());
				noteRepository.save(note);
			}
		}
		return "redirect:/notes";
	}
	
	@GetMapping("/search")
	public String search(@RequestParam(value = "date") @DateTimeFormat(iso = ISO.DATE) LocalDate date, Model model)
	{
		List<Note> notes=noteRepository.search(date);//чтобы не писать LIKE '%word%'в методе
		model.addAttribute("notes", notes);
		return "notes";
	}

}
