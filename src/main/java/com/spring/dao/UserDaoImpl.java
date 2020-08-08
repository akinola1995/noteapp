package com.spring.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.UserNotFoundException;
import com.spring.model.User;


@Repository
@Transactional
public class UserDaoImpl implements UserDAO {

	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(User user) {
		Session session = sessionFactory.getCurrentSession();
		session.save(user);
		session.flush();
		return true;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(User user) {
		if(getUserById(user.getUserId())==null) {
			return false;
		}else {
		sessionFactory.getCurrentSession().clear();
		sessionFactory.getCurrentSession().update(user);
		sessionFactory.getCurrentSession().flush();
		return true;
		}
		

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(String userId) {
		Session session = sessionFactory.getCurrentSession();
		User user =session.get(User.class, userId);
		session.flush();
		return user;
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(String userId, String password) throws UserNotFoundException {
		User user =getUserById(userId);
		if(user==null) {
			throw new UserNotFoundException("UserNotFoundException");
		}else {
			if(!password.equals(user.getUserPassword())){
			return false;	
			}
		}	
		return true;		
	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(String userId) {
		if(getUserById(userId)==null) {
			return false;
		}else {
		Session session = sessionFactory.getCurrentSession();
		session.delete(getUserById(userId));
		session.flush();
		return true;
		}

	}

}
