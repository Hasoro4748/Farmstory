package com.farmstory.repository;

import com.farmstory.entity.Order;
import com.farmstory.repository.custom.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer>, OrderRepositoryCustom {
}
