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
            authService.validatelfOrAdmin((order.getClient().getId())); /*Estamos testando se o usuario logado é dono desse pedido ou admin, se for um dos dois retorna normal abaixo, se não deve vir a exception "ForbiddenException"*/
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

        User user = userService.authenticated(); /*Estamos buscando o usuario logado. Operação para saber qual user esta autenticado que fizemos no userservice.*/
        order.setClient(user);

        for (OrderItemDTO itemDto : dto.getItems()){ /*Vamos percorrer todos os itens do "OrderItemDTO" , acessando pelo getItems do "OrderDTO"*/
            Product product = productRepository.getReferenceById(itemDto.getProductId()); /*Buscar produtos e instanciar para "product product"*/
            OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice()); /*Estamos trazendo os dados que precisamos e instanciando "OrderItem item"*/
            order.getItems().add(item); /*Adiciona um item(add(item) para a coleção de items(order.getItems().*/
        }
        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
