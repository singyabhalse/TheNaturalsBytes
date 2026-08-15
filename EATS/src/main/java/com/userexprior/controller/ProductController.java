package com.userexprior.controller;

import com.userexprior.dto.Product;
import com.userexprior.service.ElasticSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ElasticSearch productService;

    @GetMapping("/brand/{brandName}")
    public List<Product> getProducts(@PathVariable String brandName) throws IOException {
        return productService.getProductsByBrand(brandName);
    }
}
