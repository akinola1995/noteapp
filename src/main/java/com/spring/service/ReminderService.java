package com.spring.service;

import java.util.List;

import com.spring.exception.ReminderNotFoundException;
import com.spring.model.Reminder;

public interface ReminderService {
	
	public boolean createReminder(Reminder reminder);

	public Reminder updateReminder(Reminder reminder, int id) throws ReminderNotFoundException;

	public boolean deleteReminder(int reminderId);

	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException;

	public List<Reminder> getAllReminderByUserId(String userId);
}
