package com.spring.controller;

import java.util.List;

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

import com.spring.exception.CategoryNotFoundException;
import com.spring.exception.ReminderNotFoundException;
import com.spring.exception.UserAlreadyExistException;
import com.spring.model.Category;
import com.spring.model.User;
import com.spring.service.CategoryService;


@RestController
public class CategoryController {

	
	@Autowired
	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}


	
	@PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody Category category, HttpSession session) {
            if(session!=null &&session.getAttribute("loggedInUserId")!=null) {
                    if(categoryService.createCategory(category)) {                        
                        return new ResponseEntity<String>("Created",HttpStatus.CREATED);
                    }else {
                        return new ResponseEntity<String>("Conflict",HttpStatus.CONFLICT);
                    }   
            }else {
                return new ResponseEntity<String>("user id didn't match", HttpStatus.UNAUTHORIZED);
            }
    }
	/*
	 * Define a handler method which will delete a category from a database.
	 * 
	 * 
	 */
	@DeleteMapping("/category/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id, HttpSession session) {
            if(session.getAttribute("loggedInUserId")!=null ) {
                if(categoryService.deleteCategory(id))
                	return new ResponseEntity<String>("Deleted Successfully", HttpStatus.OK);
                else
                	return new ResponseEntity<String>("Conflict",HttpStatus.NOT_FOUND);
            }
            else {
                return new ResponseEntity<String>("User not found", HttpStatus.UNAUTHORIZED);
            }
        
    }

	
	
	@PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@RequestBody Category category, HttpSession session) {
		try {
            if (session.getAttribute("loggedInUserId")==null) {
                return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
            }
            Category categoryUpdate = categoryService.updateCategory(category, category.getCategoryId());
            if (categoryUpdate != null)
                return new ResponseEntity<Category>(category, HttpStatus.OK);
            else
                return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
        } catch (NullPointerException e) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
        } 
        
    }

	/*
	 * gets category by userid
	 * 
	 
	 */
	@GetMapping("/category")
    public ResponseEntity<?> getCategoryById(HttpSession session) {
        if (session!=null && session.getAttribute("loggedInUserId") != null) {
			List<Category> categories = categoryService.getAllCategoryByUserId(session.getAttribute("loggedInUserId").toString());
			return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);

	} else {
		return new ResponseEntity<String>("Not logged in",HttpStatus.UNAUTHORIZED);
	}
    }

}
