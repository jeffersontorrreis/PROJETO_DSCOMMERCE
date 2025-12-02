package com.dscommerce.dscommerce.controllers;
import com.dscommerce.dscommerce.dto.ProductDTO;
import com.dscommerce.dscommerce.dto.UserDTO;
import com.dscommerce.dscommerce.services.ProductService;
import com.dscommerce.dscommerce.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService service;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
    @GetMapping(value = "/me")
    public UserDTO getMe(){
        UserDTO dto = service.getMe();
        return dto;
    }
}

