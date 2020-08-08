package com.spring.service;

import java.util.List;

import com.spring.exception.CategoryNotFoundException;
import com.spring.exception.NoteNotFoundException;
import com.spring.exception.ReminderNotFoundException;
import com.spring.model.Note;

public interface NoteService {
	
	public boolean createNote(Note note) throws ReminderNotFoundException, CategoryNotFoundException;

	public boolean deleteNote(int noteId);

	public List<Note> getAllNotesByUserId(String userId);

	public Note getNoteById(int noteId) throws NoteNotFoundException;

	public Note updateNote(Note note, int id)
			throws ReminderNotFoundException, NoteNotFoundException, CategoryNotFoundException;
}
