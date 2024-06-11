package com.duk.dukscoffee.controllers;

import java.util.List;
import java.util.stream.Collectors;

import com.duk.dukscoffee.http.DTO.BatchesDTO;
import com.duk.dukscoffee.http.DTO.CategoryDTO;
import com.duk.dukscoffee.http.DTO.StatsProductsDTO;
import com.duk.dukscoffee.http.response.HttpResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.ok(
                productService.getProducts().stream().map(product -> {
                    ProductDTO productDTO = new ProductDTO();
                    CategoryDTO categoryDTO = new CategoryDTO();
                    BeanUtils.copyProperties(product, productDTO);
                    if (product.getCategory() != null) {
                        BeanUtils.copyProperties(product.getCategory(), categoryDTO);
                        productDTO.setCategory(categoryDTO);
                    }
                    productDTO.setStock(product.getAmount());
                    return productDTO;
                }).collect(Collectors.toList()));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductDetails(@PathVariable Integer productId)
            throws ProductNotFoundException {
        ProductDTO product = new ProductDTO();
        BeanUtils.copyProperties(productService.getProductDetails(productId), product);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/stats")
    public StatsProductsDTO getProductsWithoutCategories() {
        return productService.getStats();
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<HttpResponse> deleteProduct(@PathVariable Integer productId) throws ProductNotFoundException {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
                        "Product deleted successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<HttpResponse> deleteProductsByBatches(@RequestBody BatchesDTO productsId) {
        productService.deleteProductsByBatches(productsId.getProductsIds());
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
                        "Products deleted successfully"),
                HttpStatus.OK);
    }


    @PostMapping("/create")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateProduct(@RequestBody ProductDTO productDTO)
            throws ProductNotFoundException {
        productService.updateProduct(productDTO.getId(), productDTO);
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
                        "Product updated successfully"),
                HttpStatus.OK);
    }

    @PostMapping("/deactivate-batches")
    public ResponseEntity<HttpResponse> deactivateProductsByBatches(@RequestBody BatchesDTO productsId) {
       try {
        productService.deactivateProductsByBatches(productsId.getProductsIds());
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
                        "Productos desactivados correctamente"),
                HttpStatus.OK);
       } catch (Exception e) {

         return new ResponseEntity<>(
                new HttpResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "No se encontraron algunos productos"),
                HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping("/activate-batches")
    public ResponseEntity<HttpResponse> eactivateProductsByBatches(@RequestBody BatchesDTO productsId) {
       try {
        productService.activateProductsByBatches(productsId.getProductsIds());
        return new ResponseEntity<>(
                new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, HttpStatus.OK.getReasonPhrase(),
                        "Productos desactivados correctamente"),
                HttpStatus.OK);
       } catch (Exception e) {

         return new ResponseEntity<>(
                new HttpResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase(),
                        "No se encontraron algunos productos"),
                HttpStatus.NOT_FOUND);
       }
    }

}
