package com.spring.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.spring.dao.CategoryDAO;
import com.spring.dao.NoteDAO;
import com.spring.dao.ReminderDAO;
import com.spring.exception.CategoryNotFoundException;
import com.spring.exception.NoteNotFoundException;
import com.spring.exception.ReminderNotFoundException;
import com.spring.model.Category;
import com.spring.model.Note;
import com.spring.model.Reminder;


@Service
public class NoteServiceImpl implements NoteService {

	
	private NoteDAO noteDAO;
	private CategoryDAO categoryDAO;
	private ReminderDAO reminderDAO;
	
	public NoteServiceImpl(NoteDAO noteDAO, CategoryDAO categoryDAO, ReminderDAO reminderDAO) {
		super();
		this.noteDAO = noteDAO;
		this.categoryDAO = categoryDAO;
		this.reminderDAO = reminderDAO;
	}

	
	/*
	 * This method should be used to save a new note.
	 */

	public boolean createNote(Note note) throws ReminderNotFoundException, CategoryNotFoundException {
		Reminder reminder = note.getReminder();
		Category category = note.getCategory();
		try {
			if(reminder!=null) 
				reminderDAO.getReminderById(reminder.getReminderId());
		}catch(ReminderNotFoundException rnf) {
			throw new ReminderNotFoundException("ReminderNotFoundException");
		}
		try {
			if(category!=null)
				categoryDAO.getCategoryById(category.getCategoryId());
		}catch(CategoryNotFoundException cnf) {
			throw new  CategoryNotFoundException("CategoryNotFoundException");
		}
		
		return noteDAO.createNote(note);
		
	}

	/* This method should be used to delete an existing note. */

	public boolean deleteNote(int noteId) {
			boolean deletedNote = noteDAO.deleteNote(noteId);
			if(!deletedNote)
				return false;
			else
				return true;

	}
	/*
	 * This method should be used to get a note by userId.
	 */

	public List<Note> getAllNotesByUserId(String userId) {
		return noteDAO.getAllNotesByUserId(userId);
	}

	/*
	 * This method should be used to get a note by noteId.
	 */
	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Note note = noteDAO.getNoteById(noteId);
		if(note==null)
			throw new NoteNotFoundException("NoteNotFoundException");
		else
		return note;

	}

	/*
	 * This method should be used to update a existing note.
	 */

	public Note updateNote(Note note, int id)
			throws ReminderNotFoundException, NoteNotFoundException, CategoryNotFoundException {
		Note noteFound = noteDAO.getNoteById(id);
		Reminder reminder = note.getReminder();
		Category category = note.getCategory();
		if(noteFound==null) {
			throw new NoteNotFoundException("NoteNotFoundException");
		}else {
			noteDAO.UpdateNote(noteFound);
		}
		try {
			if(reminder!=null) 
				reminderDAO.getReminderById(reminder.getReminderId());
		}catch(ReminderNotFoundException rnf) {
			rnf.printStackTrace();
		}
		try {
			if(category!=null)
				categoryDAO.getCategoryById(category.getCategoryId());
		}catch(CategoryNotFoundException cnf) {
			cnf.printStackTrace();
		}
		
		return note;

	}

}
