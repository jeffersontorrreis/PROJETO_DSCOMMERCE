package com.dscommerce.dscommerce.repositories;
import com.dscommerce.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
