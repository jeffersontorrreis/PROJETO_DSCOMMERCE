package com.dscommerce.dscommerce.controllers;

import com.dscommerce.dscommerce.dto.OrderDTO;
import com.dscommerce.dscommerce.dto.ProductDTO;
import com.dscommerce.dscommerce.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping(value = "/{id}")
    public OrderDTO findById(@PathVariable Long id){
        return service.findById(id);
    }

    @PreAuthorize("hasAnyRole('ROLE_OPERATOR')")
    @PostMapping
    public OrderDTO insert(@Valid @RequestBody OrderDTO dto) { /*Colocamos um notEmpity em cima da lista de <OrderItemDTO> do OrderDTO*/
        dto = service.insert(dto);
        return dto;
    }
}
