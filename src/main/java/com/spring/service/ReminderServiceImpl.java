package com.spring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.spring.dao.ReminderDAO;
import com.spring.exception.CategoryNotFoundException;
import com.spring.exception.ReminderNotFoundException;
import com.spring.model.Reminder;


@Service
public class ReminderServiceImpl implements ReminderService {

	
	private ReminderDAO reminderDAO;
	
	public ReminderServiceImpl(ReminderDAO reminderDAO) {
		super();
		this.reminderDAO = reminderDAO;
	}

	

	/*
	 * This method should be used to save a new reminder.
	 */

	
	public boolean createReminder(Reminder reminder) {
		if(reminderDAO.createReminder(reminder))
			return true;
		else
			return false;

	}

	/*
	 * This method should be used to update a existing reminder.
	 */

	public Reminder updateReminder(Reminder reminder, int id) throws ReminderNotFoundException {
		reminderDAO.updateReminder(reminder);
		Reminder updatedReminder =getReminderById(id);
		if(updatedReminder==null)
			throw new ReminderNotFoundException("ReminderNotFoundException");
		else
			return reminder;
	}

	/* This method should be used to delete an existing reminder. */
	
	public boolean deleteReminder(int reminderId) {
		if(reminderDAO.deleteReminder(reminderId))
			return true;
		else
			return false;
	}

	/*
	 * This method should be used to get a reminder by reminderId.
	 */
	
	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Reminder reminder = reminderDAO.getReminderById(reminderId);
		if(reminder==null)
			throw new ReminderNotFoundException("ReminderNotFoundException");
		else
			return reminder;

	}

	/*
	 * This method should be used to get a reminder by userId.
	 */

	public List<Reminder> getAllReminderByUserId(String userId) {
		return reminderDAO.getAllReminderByUserId(userId);

	}
}
