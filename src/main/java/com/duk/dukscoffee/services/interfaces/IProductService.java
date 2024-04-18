package com.duk.dukscoffee.services.interfaces;

import java.util.List;

import com.duk.dukscoffee.entities.Product;

import com.duk.dukscoffee.exceptions.ProductNotFoundException;
import com.duk.dukscoffee.http.DTO.ProductDTO;
import com.duk.dukscoffee.http.DTO.StatsProductsDTO;

public interface IProductService {

    public ProductDTO createProduct(ProductDTO productDTO);
    public List<Product> getProducts();
     public ProductDTO getProductDetails(Integer productId)throws ProductNotFoundException;
     public StatsProductsDTO getStats();
     public void deleteProduct(Integer productId) throws ProductNotFoundException;
     public void deleteProductsByBatches(List<Integer> productsId);

}
