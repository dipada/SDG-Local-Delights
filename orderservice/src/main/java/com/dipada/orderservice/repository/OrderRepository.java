package com.dipada.orderservice.repository;

import com.dipada.orderservice.model.Order;
import com.dipada.orderservice.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<List<Order>> findAllOrdersByUserEmail(String userEmail);

	Optional<List<Order>> findAllOrdersByOrderStatus(OrderStatus orderStatus);

	Optional<List<Order>> findAllOrdersByDeliveryEmail(String deliveryEmail);

	Optional<List<Order>> findAllOrdersByShopId(Long shopId);
}
