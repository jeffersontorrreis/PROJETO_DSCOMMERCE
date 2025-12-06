package com.dscommerce.dscommerce.repositories;

import com.dscommerce.dscommerce.entities.Order;
import com.dscommerce.dscommerce.entities.OrderItem;
import com.dscommerce.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
