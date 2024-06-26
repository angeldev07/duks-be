package com.duk.dukscoffee.services.implementations;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;

import com.duk.dukscoffee.entities.Category;
import com.duk.dukscoffee.entities.Product;
import com.duk.dukscoffee.exceptions.CategoryExistException;
import com.duk.dukscoffee.exceptions.CategoryNotFoundException;
import com.duk.dukscoffee.http.DTO.CategoryDTO;
import com.duk.dukscoffee.respositories.CategoryRepository;
import com.duk.dukscoffee.respositories.ProductRepository;
import com.duk.dukscoffee.services.interfaces.ICategoryService;

@Service
public class CategoryService implements ICategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public static final String IS_NOT_FOUND = "The %s is not found";
    public static final String IS_ALREADY_USE = "The %s is already use";

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws CategoryExistException{

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDeleteFlag(false);
        category.setActive(categoryDTO.getActive());
        Category newCategory = categoryRepository.save(category);
        CategoryDTO newCategoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(newCategory, newCategoryDTO);

        return newCategoryDTO;
    }

    @Override
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

    @Override
    public List<Category> getCategories(){
        return (List<Category>) categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Integer categoryId) throws CategoryNotFoundException{
        Category category = categoryRepository.findById(categoryId).orElse(null);
        
        if(category == null){
            throw new CategoryNotFoundException(String.format(IS_NOT_FOUND, "category").toUpperCase()); 
        }

        this.updateProductsCategory(categoryId);

        categoryRepository.delete(category);
    }

    @Override
    public void deleteCategoriesByBatches(List<Integer> categoriesId){
        List<Category> categories = categoryRepository.findAllById(categoriesId);
        for (Category category : categories) {
            this.updateProductsCategory(category.getId());
            categoryRepository.delete(category);
        }
    }

    @Override
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

    @Override
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

    private void updateProductsCategory(Integer categoryId){
        List<Product> products = productRepository.findByCategoryId(categoryId);
        products.forEach(product -> {
            product.setCategory(null);
            productRepository.save(product);
        });
    }

    public void enableCategoryByBatches(List<Integer> productsIds, String option) {
       String[] options = {"on", "off"}; 

        // validamos que la opción sea válida    
        if(!options[0].equals(option) && !options[1].equals(option)){
            throw new MethodNotAllowedException("THE OPTION IS NOT ALLOWED", null);
        }


        List<Category> categories = categoryRepository.findAllById(productsIds);

        categories.forEach(category -> {
           
            if(options[0].equalsIgnoreCase(option))
                category.setActive(true);
            else
                category.setActive(false);

            categoryRepository.save(category);
        });

    }

}
