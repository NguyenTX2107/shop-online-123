package com.assignment.shoponline.repository;

import com.assignment.shoponline.entity.order.OrderDetail;
import com.assignment.shoponline.entity.order.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}
