package com.spring.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.exception.CategoryNotFoundException;
import com.spring.model.Category;
import com.spring.model.User;

/*
 * This class implements the UserDAO interface. 
 * */
@Repository
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public CategoryDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new category
	 */
	public boolean createCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.save(category);
		session.flush();
		return true;

	}

	/*
	 * Remove an existing category
	 */
	public boolean deleteCategory(int categoryId) {
		boolean flag = true;
		try {
			if(getCategoryById(categoryId)==null) {
				flag = false;
			}else {
				Session session = sessionFactory.getCurrentSession();
				session.delete(getCategoryById(categoryId));
				session.flush();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
		}
		return flag;

	}
	/*
	 * Update an existing category
	 */

	public boolean updateCategory(Category category) {
		boolean flag = true;
		try {
			if(getCategoryById(category.getCategoryId())==null) {
				flag = false;
			}else {
				sessionFactory.getCurrentSession().clear();
				sessionFactory.getCurrentSession().update(category);
				sessionFactory.getCurrentSession().flush();
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/*
	 * Retrieve details of a specific category
	 */

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Category category = session.get(Category.class, categoryId);
		if(category==null)
			throw new CategoryNotFoundException("CategoryNotFoundException");
		else {
			session.flush();
			return category;
		}
			

	}

	/*
	 * Retrieve details of all categories by userId
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		String hql = "From Category category where categoryCreatedBy = :userId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql).setParameter("userId", userId);
		List result = query.getResultList();
		return result;
	}

}
