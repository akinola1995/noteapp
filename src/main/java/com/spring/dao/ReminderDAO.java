package com.spring.dao;

import java.util.List;

import com.spring.exception.ReminderNotFoundException;
import com.spring.model.Reminder;

public interface ReminderDAO {
	

	public boolean createReminder(Reminder reminder);

	public boolean updateReminder(Reminder reminder);

	public boolean deleteReminder(int reminderId);

	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException;

	public List<Reminder> getAllReminderByUserId(String userId);
}
