package com.spring.controller;

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

import com.spring.exception.UserAlreadyExistException;
import com.spring.model.User;
import com.spring.service.UserService;


@RestController
public class UserController {

	
	@Autowired
	private UserService userService;
	
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}


	
	@PostMapping("/user/register")
	public ResponseEntity<?>registerUser(@RequestBody User user) {
		try {
			userService.registerUser(user);
			return new ResponseEntity<User>(user,HttpStatus.CREATED);
		}catch(UserAlreadyExistException e) {
			return new ResponseEntity<User>(user,HttpStatus.CONFLICT);
		}
		
		
	}

	@PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user, HttpSession session) {
        try {
            if (!session.getAttribute("loggedInUserId").equals(user.getUserId())) {
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            User userUpdated = userService.updateUser(user, user.getUserId());
            if (userUpdated != null) {
                return new ResponseEntity<User>(user, HttpStatus.OK);
            }
            else
                return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
        }
    }

	

	@DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id, HttpSession session) {
        try {
            if(session.getAttribute("loggedInUserId")!=null && session.getAttribute("loggedInUserId").equals(id)) {
                if(!userService.deleteUser(id))
                    throw new Exception("not found");
                else
                    return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>("User not found", HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
    }
	
	@GetMapping("/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id,User user, HttpSession session) {
        try {
            if(session.getAttribute("loggedInUserId")!=null && session.getAttribute("loggedInUserId").equals(id)) {
                if(userService.getUserById(id)==null)
                    throw new Exception("not found");
                else
                    return new ResponseEntity<User>(user, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<String>("User not found", HttpStatus.UNAUTHORIZED);
            }
        } catch(Exception e) {
            return new ResponseEntity<String>("User not found", HttpStatus.NOT_FOUND);
        }
    }
	
}