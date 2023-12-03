package com.duk.dukscoffee.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.MethodNotAllowedException;

import com.duk.dukscoffee.exceptions.CardIdExistException;
import com.duk.dukscoffee.exceptions.CategoryExistException;
import com.duk.dukscoffee.exceptions.CategoryNotFoundException;
import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.EmailExistException;
import com.duk.dukscoffee.exceptions.ExceptionHandling;
import com.duk.dukscoffee.http.DTO.CategoryDTO;
import com.duk.dukscoffee.http.DTO.ClientDTO;
import com.duk.dukscoffee.http.response.HttpResponse;
import com.duk.dukscoffee.services.implementations.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController extends ExceptionHandling {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryExistException {
        return ResponseEntity.ok(categoryService.createCategory(categoryDTO));
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<HttpResponse> updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryDTO categoryDTO) throws CategoryNotFoundException {
        categoryService.updateCategory(categoryId, categoryDTO);

        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Category updated successfully"),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<HttpResponse> deleteCategory(@PathVariable Integer categoryId) throws CategoryNotFoundException {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Category deleted successfully"),
                HttpStatus.OK
        );
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        return ResponseEntity.ok(
                categoryService.getCategories().stream().map(category -> {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    BeanUtils.copyProperties(category, categoryDTO);
                    return categoryDTO;
                }).collect(Collectors.toList())
        );
    }

    @PutMapping("/on")
    public ResponseEntity<HttpResponse> enableCategory(@RequestParam Integer categoryId) throws CategoryNotFoundException {
        categoryService.enableCategory(categoryId);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Category enabled successfully"),
                HttpStatus.OK
        );
    }

    @PutMapping("/off")
    public ResponseEntity<HttpResponse> disableCategory(@RequestParam Integer categoryId) throws CategoryNotFoundException {
        categoryService.disableCategory(categoryId);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Category disabled successfully"),
                HttpStatus.OK
        );
    }

}