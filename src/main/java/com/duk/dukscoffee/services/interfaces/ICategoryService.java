package com.duk.dukscoffee.services.interfaces;

import java.util.List;

import com.duk.dukscoffee.entities.Category;
import com.duk.dukscoffee.exceptions.CategoryExistException;
import com.duk.dukscoffee.exceptions.CategoryNotFoundException;
import com.duk.dukscoffee.http.DTO.CategoryDTO;

public interface ICategoryService {
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws CategoryExistException;
    public CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO) throws CategoryNotFoundException;
    public List<Category> getCategories();
    public void deleteCategory(Integer categoryId) throws CategoryNotFoundException;
    public void enableCategory(Integer categoryId) throws CategoryNotFoundException;
    public void disableCategory(Integer categoryId) throws CategoryNotFoundException;

}
