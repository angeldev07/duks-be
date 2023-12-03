package com.duk.dukscoffee.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.duk.dukscoffee.http.DTO.StatsProductsDTO;
import com.duk.dukscoffee.http.response.HttpResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duk.dukscoffee.exceptions.ClientNotFoundException;
import com.duk.dukscoffee.exceptions.ProductNotFoundException;
import com.duk.dukscoffee.http.DTO.ProductDTO;
import com.duk.dukscoffee.services.implementations.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(){
        return ResponseEntity.ok(
                  productService.getProducts().stream().map(product -> {
                      ProductDTO productDTO = new ProductDTO();
                      BeanUtils.copyProperties(product, productDTO);
                      return productDTO;
                  }).collect(Collectors.toList())
          );
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductDetails(@PathVariable Integer productId) throws ProductNotFoundException{
        ProductDTO product = new ProductDTO();
        BeanUtils.copyProperties(productService.getProductDetails(productId), product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/stats")
    public StatsProductsDTO getProductsWithoutCategories(){
        return productService.getStats();
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable Integer productId) throws ProductNotFoundException{
        productService.deleteProduct(productId);
        return new ResponseEntity<>( new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(), "Product deleted successfully"),
                 HttpStatus.OK
         );
    }


}