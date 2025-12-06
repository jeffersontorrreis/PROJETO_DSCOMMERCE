package com.dscommerce.dscommerce.repositories;

import com.dscommerce.dscommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
