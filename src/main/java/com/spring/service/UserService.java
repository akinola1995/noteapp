package com.spring.service;

import com.spring.exception.UserAlreadyExistException;
import com.spring.exception.UserNotFoundException;
import com.spring.model.User;

public interface UserService {
	
	public boolean registerUser(User user) throws UserAlreadyExistException;

	public User updateUser(User user, String id) throws Exception;

	public boolean deleteUser(String UserId);

	public boolean validateUser(String userName, String password) throws UserNotFoundException;

	public User getUserById(String userId) throws UserNotFoundException;

}
