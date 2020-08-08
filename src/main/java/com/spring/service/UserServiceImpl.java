package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.UserDAO;
import com.spring.exception.UserAlreadyExistException;
import com.spring.exception.UserNotFoundException;
import com.spring.model.User;


@Service
public class UserServiceImpl implements UserService {

	
	@Autowired
	private UserDAO userDAO;
	
	public UserServiceImpl(UserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}
	/*
	 * This method should be used to save a new user.
	 */


	public boolean registerUser(User user) throws UserAlreadyExistException {
		if(userDAO.registerUser(user)) 
			return true;
		else 
			throw new UserAlreadyExistException("UserAlreadyExistException");
	}

	/*
	 * This method should be used to update a existing user.
	 */

	public User updateUser(User user, String userId) throws Exception {
		userDAO.updateUser(user);
		User newUser = getUserById(userId);
		if(newUser==null)
			throw new Exception();
		else 
			return user;
}


	/*
	 * This method should be used to get a user by userId.
	 */

	public User getUserById(String UserId) throws UserNotFoundException {
		User user = userDAO.getUserById(UserId);
		if(user==null)
			throw new UserNotFoundException("UserNotFoundException");
		else
			return user;

	}

	/*
	 * This method should be used to validate a user using userId and password.
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		boolean validUser = userDAO.validateUser(userId, password);
		if(!validUser)
			throw new UserNotFoundException("UserNotFoundException");
		else 
			return true;

	}

	/* This method should be used to delete an existing user. */
	public boolean deleteUser(String UserId) {
		boolean deleteUser = userDAO.deleteUser(UserId);
			if(!deleteUser) {
				return false;
			}else {
				return true;
			}		

	}

}
