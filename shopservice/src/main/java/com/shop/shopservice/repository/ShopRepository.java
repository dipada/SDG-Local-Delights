package com.shop.shopservice.repository;

import com.shop.shopservice.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
	List<Shop> findBySellerEmail(String email);

	Optional<Shop> findByIdAndSellerEmail(Long shopId, String sellerEmail);
}
