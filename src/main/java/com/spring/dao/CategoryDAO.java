package com.spring.dao;

import java.util.List;

import com.spring.exception.CategoryNotFoundException;
import com.spring.model.Category;

public interface CategoryDAO {

	

	public boolean createCategory(Category category);

	public boolean deleteCategory(int noteId);

	public boolean updateCategory(Category category);

	public Category getCategoryById(int categoryId) throws CategoryNotFoundException;

	public List<Category> getAllCategoryByUserId(String userId);
}
