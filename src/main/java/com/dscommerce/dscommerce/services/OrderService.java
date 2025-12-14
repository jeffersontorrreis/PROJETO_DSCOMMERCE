package com.dscommerce.dscommerce.services;

import com.dscommerce.dscommerce.dto.OrderDTO;
import com.dscommerce.dscommerce.dto.OrderItemDTO;
import com.dscommerce.dscommerce.dto.ProductDTO;
import com.dscommerce.dscommerce.entities.*;
import com.dscommerce.dscommerce.repositories.OrderItemRepository;
import com.dscommerce.dscommerce.repositories.OrderRepository;
import com.dscommerce.dscommerce.repositories.ProductRepository;
import com.dscommerce.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        try {
            Order order = repository.findById(id).get();
            authService.validatelfOrAdmin((order.getClient().getId())); 
            return new OrderDTO(order);
        }
        catch (NoSuchElementException e) {
            throw new ResourceNotFoundException("Pedido não encontrado!");
        }
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto){
        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated(); 
        order.setClient(user);

        for (OrderItemDTO itemDto : dto.getItems()){ 
            Product product = productRepository.getReferenceById(itemDto.getProductId()); 
            OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice()); 
            order.getItems().add(item); 
        }
        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
