package com.product.productservice.repository;

import com.product.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByShopId(Long shopId);
}
