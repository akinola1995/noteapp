package com.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.dao.CategoryDAO;
import com.spring.exception.CategoryNotFoundException;
import com.spring.model.Category;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	public CategoryServiceImpl(CategoryDAO categoryDAO) {
		super();
		this.categoryDAO = categoryDAO;
	}

	/*
	 * This method should be used to save a new category.
	 */
	public boolean createCategory(Category category) {
		if(categoryDAO.createCategory(category)) 
			return true;
		else
			return false;

	}

	/* This method should be used to delete an existing category. */
	public boolean deleteCategory(int categoryId) {
		if(categoryDAO.deleteCategory(categoryId))
			return true;
		else
			return false;

	}

	/*
	 * This method should be used to update a existing category.
	 */

	public Category updateCategory(Category category, int id) throws CategoryNotFoundException {
		categoryDAO.updateCategory(category);
		Category updatedCategory = getCategoryById(id);
		if(updatedCategory==null)
			throw new CategoryNotFoundException("CategoryNotFoundException");
		else
			return category;

	}

	/*
	 * This method should be used to get a category by categoryId.
	 */
	public Category getCategoryById(int categoryId) throws CategoryNotFoundException {
		Category category = categoryDAO.getCategoryById(categoryId);
		if(category==null)
			throw new CategoryNotFoundException("CategoryNotFoundException");
		else
			return category;

	}

	/*
	 * This method should be used to get a category by userId.
	 */

	public List<Category> getAllCategoryByUserId(String userId) {
		return categoryDAO.getAllCategoryByUserId(userId);
	}

}
