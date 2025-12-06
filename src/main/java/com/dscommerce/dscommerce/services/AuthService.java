package com.dscommerce.dscommerce.services;

import com.dscommerce.dscommerce.entities.User;
import com.dscommerce.dscommerce.services.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validatelfOrAdmin(Long userId){
        User me = userService.authenticated(); /*Pegamos o usuario auetenticado e mandamos ele para a varoavel "me"*/
        if(!me.hasRole("ROLE_ADMIN") && !me.getId().equals(userId)){ /*Se o meu usuario não for admin e nem for esse "userId" que chegou como argumento lançamos a exception abaixo:*/
            throw new ForbiddenException("Access denied");
        }
    }
}
