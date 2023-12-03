package com.duk.dukscoffee.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import com.duk.dukscoffee.http.DTO.CategoryDTO;
import com.duk.dukscoffee.http.DTO.StatsProductsDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;


import com.duk.dukscoffee.entities.Product;

import com.duk.dukscoffee.exceptions.ProductNotFoundException;
import com.duk.dukscoffee.http.DTO.ProductDTO;
import com.duk.dukscoffee.respositories.ProductRepository;
import com.duk.dukscoffee.services.interfaces.IProductService;

@Service
public class ProductService implements IProductService {

    public static final String IS_ALREADY_USE = "The %s is already use";
    public static final String IS_NOT_FOUND = "The %s is not found";
    public static final String IS_NOT_ALLOWED = "The %s is not allowed";


    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return (List<Product>) productRepository.findAll();
    }

    @Override
    public ProductDTO getProductDetails(Integer productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null)
            throw new ProductNotFoundException(String.format(IS_NOT_FOUND, "product").toUpperCase());
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    @Override
    public StatsProductsDTO getStats() {
        return new StatsProductsDTO(
                getProductsWithoutCategories(),
                getProductsLowStock(),
                getProductsDeactivate()
        );
    }

    public List<ProductDTO> getProductsWithoutCategories() {
        return productRepository.findByNoCategory().stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            BeanUtils.copyProperties(product, productDTO);
            productDTO.setCategory(null);
            return  productDTO;
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsDeactivate() {
        return productRepository.findByActive().stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(product, productDTO);
            BeanUtils.copyProperties(product.getCategory(), categoryDTO);
            productDTO.setCategory(categoryDTO);
            return  productDTO;
        }).collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsLowStock() {
        return productRepository.findByLowStock().stream().map(product -> {
            ProductDTO productDTO = new ProductDTO();
            CategoryDTO categoryDTO = new CategoryDTO();
            BeanUtils.copyProperties(product, productDTO);
            BeanUtils.copyProperties(product.getCategory(), categoryDTO);
            productDTO.setCategory(categoryDTO);
            return  productDTO;
        }).collect(Collectors.toList());
    }


    public void EnableProduct(Integer productId) throws ProductNotFoundException {
        Product product = productRepository.findById(productId).orElse(null);
        if (product == null)
            throw new ProductNotFoundException(String.format(IS_NOT_FOUND, "product").toUpperCase());
        if (product.isActive())
            throw new MethodNotAllowedException("YOU CAN'T ENABLE THE CATEGORY BECAUSE THIS IS CURRENTLY ENABLED", null);
        product.setActive(true);
        productRepository.save(product);
    }

    // ---------------------------------------CLASS
    // METHOD------------------------------------

}
