package com.duk.dukscoffee.services.interfaces;

import java.util.List;

import com.duk.dukscoffee.entities.Product;

import com.duk.dukscoffee.exceptions.ProductNotFoundException;
import com.duk.dukscoffee.http.DTO.ProductDTO;

public interface IProductService {
    
    public List<Product> getProducts();
     public ProductDTO getProductDetails(Integer productId)throws ProductNotFoundException;
     

}
