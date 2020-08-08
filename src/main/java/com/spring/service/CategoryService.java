package com.spring.service;

import java.util.List;

import com.spring.exception.CategoryNotFoundException;
import com.spring.model.Category;

public interface CategoryService {

	
	public boolean createCategory(Category category);

	public boolean deleteCategory(int noteId);

	public Category updateCategory(Category category, int id) throws CategoryNotFoundException;

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException;

	public List<Category> getAllCategoryByUserId(String userId);

}
