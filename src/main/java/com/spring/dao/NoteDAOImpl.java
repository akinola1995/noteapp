package com.spring.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.NoteNotFoundException;
import com.spring.model.Note;


@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public NoteDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new note
	 */
	
	public boolean createNote(Note note) {
		Session session =sessionFactory.getCurrentSession();
		session.save(note);
		session.flush();
		return true;

	}

	/*
	 * Remove an existing note
	 */
	
	public boolean deleteNote(int noteId) {
		boolean flag = true;
		try {
			if(getNoteById(noteId)==null) {
				flag = false;
			}else {
				Session session = sessionFactory.getCurrentSession();
				session.delete(getNoteById(noteId));
				session.flush();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (NoteNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
		
	}

	/*
	 * Retrieve details of all notes by userId
	 */
	
	public List<Note> getAllNotesByUserId(String userId) {
		String hql = "FROM Note note where createdBy =  :userId ";
        Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId);
		List result = query.getResultList();
		return result;

	}

	/*
	 * Retrieve details of a specific note
	 */
	
	

	public Note getNoteById(int noteId) throws NoteNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Note note =session.get(Note.class, noteId);
		if(note==null)
			throw new NoteNotFoundException("NoteNotFoundException");
		else {
			session.flush();
			return note;
		}

	}

	/*
	 * Update an existing note	
	 */

	public boolean UpdateNote(Note note) {
		boolean flag=true;
		try {
			if(getNoteById(note.getNoteId())==null) {
				flag=false;
			}else {
				sessionFactory.getCurrentSession().clear();
				sessionFactory.getCurrentSession().update(note);
				sessionFactory.getCurrentSession().flush();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (NoteNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}

}