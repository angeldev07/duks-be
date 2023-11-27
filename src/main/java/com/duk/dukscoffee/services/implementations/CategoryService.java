package com.duk.dukscoffee.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duk.dukscoffee.entities.Category;
import com.duk.dukscoffee.exceptions.CategoryExistException;
import com.duk.dukscoffee.exceptions.CategoryNotFoundException;
import com.duk.dukscoffee.exceptions.EmailExistException;
import com.duk.dukscoffee.http.DTO.CategoryDTO;
import com.duk.dukscoffee.respositories.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public static final String IS_NOT_FOUND = "The %s is not found";
    public static final String IS_ALREADY_USE = "The %s is already use";

    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws CategoryExistException{

        ifCategoryExist(categoryDTO.getName());
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setActive(true);
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

        categoryRepository.delete(category);
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
