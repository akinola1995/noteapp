package com.spring.dao;

import com.spring.exception.UserNotFoundException;
import com.spring.model.User;

public interface UserDAO {

	
	public boolean registerUser(User user);

	public boolean updateUser(User user);

	public User getUserById(String UserId);

	public boolean validateUser(String userName, String password) throws UserNotFoundException;

	public boolean deleteUser(String UserId);
}
