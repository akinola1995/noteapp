package com.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.exception.UserNotFoundException;
import com.spring.model.User;
import com.spring.service.UserService;


@RestController
public class UserAuthenticationController {

	
	@Autowired
	private UserService userService;

	public UserAuthenticationController(UserService userService) {
		super();
		this.userService = userService;
	}

	
	@PostMapping("/login")
	public ResponseEntity<?>userLogin(@RequestBody User user,HttpSession session){
		
		try {
			if(userService.validateUser(user.getUserId(), user.getUserPassword())) {
				session.setAttribute("loggedInUserId",user.getUserId());
				return new ResponseEntity<String>("Login Successful",HttpStatus.OK); 
			}else {
				return new ResponseEntity<String>("Login Failure",HttpStatus.UNAUTHORIZED);
			}
		} catch (UserNotFoundException e) {
			return new ResponseEntity<String>("User does not exists", HttpStatus.UNAUTHORIZED);
		}
	}
	@GetMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		if(session!=null && session.getAttribute("loggedInUserId")!=null) {
			session.invalidate();
			return new ResponseEntity<String>("Logged Out", HttpStatus.OK);
		} else
			return new ResponseEntity<String>("User does not exists", HttpStatus.BAD_REQUEST);
	}

	
}
