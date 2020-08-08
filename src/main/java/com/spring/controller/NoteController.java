package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.CategoryNotFoundException;
import com.spring.exception.NoteNotFoundException;
import com.spring.exception.ReminderNotFoundException;
import com.spring.model.Note;
import com.spring.service.NoteService;


@RestController
public class NoteController {

	
	@Autowired
	private NoteService noteService;
	
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	
	@RequestMapping(value ="/note",method = RequestMethod.POST)
    public ResponseEntity<?> createNote(@RequestBody Note note, HttpSession session){
        try {
            if(session.getAttribute("loggedInUserId").equals(note.getCreatedBy())) {
                try {
                    if(noteService.createNote(note)) {                        
                        return new ResponseEntity<String>("Created",HttpStatus.CREATED);
                    }else {
                        return new ResponseEntity<String>("Conflict",HttpStatus.CONFLICT);
                    }
                }catch(ReminderNotFoundException rnfe){
                    return new ResponseEntity<String>("Reminder not found", HttpStatus.UNAUTHORIZED);
                }catch(CategoryNotFoundException cnfe) {
                    return new ResponseEntity<String>("Category not found", HttpStatus.UNAUTHORIZED);
                }
            }else {
                return new ResponseEntity<String>("Unauthorised user", HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e) {
            return new ResponseEntity<String>("No match found", HttpStatus.UNAUTHORIZED);
        }
    }

	@DeleteMapping("/note/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable int id, HttpSession session){
		try {
		if(session!=null&&session.getAttribute("loggedInUserId")!=null) {
			if(noteService.deleteNote(id))
				return new ResponseEntity<String>("Deleted", HttpStatus.OK);
			else
				throw new NoteNotFoundException("NoteNotFoundException");
		}else {
			return new ResponseEntity<String>("Note not found", HttpStatus.UNAUTHORIZED);
		}
		}catch(NoteNotFoundException e) {
			return new ResponseEntity<String>("Note not found", HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/note/{id}")
	public ResponseEntity<?> updateNote(@RequestBody Note note, HttpSession session){
		try {
		if(session!=null && session.getAttribute("loggedInUserId")!=null&&session.getAttribute("loggedInUserId").equals(note.getCreatedBy())) {
			if(noteService.updateNote(note, note.getNoteId())!=null)
				return new ResponseEntity<Note>(note, HttpStatus.OK);
			else
				throw new Exception("Not found");
				
		}else {
			return new ResponseEntity<String>("Note not found", HttpStatus.UNAUTHORIZED);
		}
		}catch(Exception e) {
			return new ResponseEntity<String>("Note not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/note")
	public ResponseEntity<?> getNotesByUserId(HttpSession session){
		if(session!=null&&session.getAttribute("loggedInUserId")!=null) {
			List<Note> notes = noteService.getAllNotesByUserId(session.getAttribute("loggedInUserId").toString());
			return new ResponseEntity<List<Note>>(notes, HttpStatus.OK);

		}else {
			return new ResponseEntity<String>("Not found",HttpStatus.UNAUTHORIZED);
		}
	}
}
