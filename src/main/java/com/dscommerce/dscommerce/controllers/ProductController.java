package com.dscommerce.dscommerce.controllers;
import com.dscommerce.dscommerce.dto.ProductDTO;
import com.dscommerce.dscommerce.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController{

    @Autowired
    private ProductService service;


    @GetMapping
    public List<ProductDTO> findAll(){
        return service.findAll();
    }



    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable Long id){
        return service.findById(id);
    }


    @PostMapping
    public ProductDTO insert(@Valid @RequestBody ProductDTO dto) {
        dto = service.insert(dto);
        return dto;
    }


    @PutMapping(value = "/{id}")
    public ProductDTO update(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        dto = service.update(id, dto);
        return dto;
    }


    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}

