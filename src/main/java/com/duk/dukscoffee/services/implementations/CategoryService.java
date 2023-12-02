package com.duk.dukscoffee.services.implementations;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;

import com.duk.dukscoffee.entities.Category;
import com.duk.dukscoffee.exceptions.CategoryExistException;
import com.duk.dukscoffee.exceptions.CategoryNotFoundException;
import com.duk.dukscoffee.http.DTO.CategoryDTO;
import com.duk.dukscoffee.respositories.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public static final String IS_NOT_FOUND = "The %s is not found";
    public static final String IS_ALREADY_USE = "The %s is already use";

    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws CategoryExistException{

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setActive(categoryDTO.getActive());
        category.setDeleteFlag(false);
        Category newCategory = categoryRepository.save(category);
        CategoryDTO newCategoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(newCategory, newCategoryDTO);

        return newCategoryDTO;
    }

    public CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO) throws CategoryNotFoundException{
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null){
            throw new CategoryNotFoundException(String.format(IS_NOT_FOUND, "category").toUpperCase()); 
        }
        category.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        CategoryDTO updateCategoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(updatedCategory, updateCategoryDTO);
        return updateCategoryDTO;
    }

    public List<Category> getCategories(){
        return (List<Category>) categoryRepository.findAll();
    }

    public void deleteCategory(Integer categoryId) throws CategoryNotFoundException{
         Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null){
            throw new CategoryNotFoundException(String.format(IS_NOT_FOUND, "category").toUpperCase()); 
        }

        category.setDeleteFlag(true);

        categoryRepository.save(category);
    }

    public void enableCategory(Integer categoryId) throws CategoryNotFoundException{
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null ) {
             throw new CategoryNotFoundException (String.format(IS_NOT_FOUND, "category").toUpperCase());  
        }
        if(category.getActive()== true){
             throw new MethodNotAllowedException("YOU CAN'T ENABLE THE CATEGORY BECAUSE THIS IS CURRENTLY ENABLED", null);
        }

        category.setActive(true);
        categoryRepository.save(category);
       
    }

    public void disableCategory(Integer categoryId) throws CategoryNotFoundException{
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if(category == null ) {
             throw new CategoryNotFoundException (String.format(IS_NOT_FOUND, "category").toUpperCase());  
        }
        if(category.getActive()== false){
             throw new MethodNotAllowedException("YOU CAN'T DISABLE THE CATEGORY BECAUSE THIS IS CURRENTLY DISABLED", null);
        }

        category.setActive(false);
        categoryRepository.save(category);
       
    }


   


      /************************************** CLASS METHOD  *******************************************/

      private void ifCategoryExist(String nameCategory) throws CategoryExistException{
        Category category = categoryRepository.findByName(nameCategory).orElse(null);
        if(category == null) return;

        if (category.getName().equalsIgnoreCase(nameCategory)) {
             throw new CategoryExistException (String.format(IS_ALREADY_USE, "email").toUpperCase());
        }

      }


}
