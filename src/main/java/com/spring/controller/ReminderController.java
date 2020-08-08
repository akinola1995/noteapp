package com.spring.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.ReminderNotFoundException;
import com.spring.model.Category;
import com.spring.model.Reminder;
import com.spring.service.ReminderService;


@RestController
public class ReminderController {


	@Autowired
	private ReminderService reminderService;

	public ReminderController(ReminderService reminderService) {
		this.reminderService = reminderService;
	}

	
	@PostMapping("/reminder")
	public ResponseEntity<?>addReminder(@RequestBody Reminder reminder,HttpSession session){
		if(session!=null &&session.getAttribute("loggedInUserId")!=null) {
			if(reminderService.createReminder(reminder))
				return new ResponseEntity<Reminder>(reminder, HttpStatus.CREATED);
			else
				return new ResponseEntity<String>("Reminder can't be created", HttpStatus.CONFLICT);
			
		}else {
			return new ResponseEntity<Reminder>(reminder, HttpStatus.UNAUTHORIZED);
		}
	}


	@DeleteMapping("/reminder/{id}")
	public ResponseEntity<?>deleteReminder(@PathVariable int id,HttpSession session){
		if(session!=null &&session.getAttribute("loggedInUserId")!=null) {
			if(reminderService.deleteReminder(id))
				return new ResponseEntity<String>("Reminder Deleted", HttpStatus.OK);
			else
				return new ResponseEntity<String>("Reminder can't be deleted", HttpStatus.NOT_FOUND);
			
		}else {
			return new ResponseEntity<String>("Not logged in", HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PutMapping("/reminder/{id}")
	public ResponseEntity<?> update(@RequestBody Reminder reminder, HttpSession session) {
		try {
			if(session !=null && session.getAttribute("loggedInUserId")!=null && session.getAttribute("loggedInUserId").equals(reminder.getReminderCreatedBy())) {
				if(reminderService.updateReminder(reminder, reminder.getReminderId())==null)
					throw new ReminderNotFoundException("not found");
				return new ResponseEntity<Reminder>(reminder, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("Note not found", HttpStatus.UNAUTHORIZED);
			}
		} catch(ReminderNotFoundException e) {
			return new ResponseEntity<String>("Note not found", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/reminder")
    public ResponseEntity<?> getReminderById(HttpSession session) {
        if (session!=null && session.getAttribute("loggedInUserId") != null) {
			List<Reminder> reminders = reminderService.getAllReminderByUserId(session.getAttribute("loggedInUserId").toString());
			return new ResponseEntity<List<Reminder>>(reminders, HttpStatus.OK);

	} else {
		return new ResponseEntity<String>("Not logged in",HttpStatus.UNAUTHORIZED);
	}
    }
	
	@GetMapping("/reminder/{id}")
	public ResponseEntity<?> getReminderById(@PathVariable int id, HttpSession session) {
		if (session!=null && session.getAttribute("loggedInUserId") != null) {
				try {
					Reminder reminder = reminderService.getReminderById(id);
					if(reminder==null) {
						return new ResponseEntity<String>("not found", HttpStatus.NOT_FOUND);
					} else {
						return new ResponseEntity<Reminder>(reminder, HttpStatus.OK);
					}
				} catch (ReminderNotFoundException e) {
					return new ResponseEntity<String>("not found", HttpStatus.NOT_FOUND);
				}
		} else {
			return new ResponseEntity<String>("Not logged in",HttpStatus.UNAUTHORIZED);
		}
	}

}