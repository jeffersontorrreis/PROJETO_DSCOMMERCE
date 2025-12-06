package com.dscommerce.dscommerce.repositories;

import com.dscommerce.dscommerce.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
