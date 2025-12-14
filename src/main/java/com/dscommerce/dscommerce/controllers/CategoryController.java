package com.dscommerce.dscommerce.controllers;
import com.dscommerce.dscommerce.dto.CategoryDTO;
import com.dscommerce.dscommerce.dto.ProductDTO;
import com.dscommerce.dscommerce.dto.ProductMinDTO;
import com.dscommerce.dscommerce.services.CategoryService;
import com.dscommerce.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

   
    @GetMapping
    public List<CategoryDTO> findAll(){
        List<CategoryDTO> list = service.findAll();
        return list;
    }

}

