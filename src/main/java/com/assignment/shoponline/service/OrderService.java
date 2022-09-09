package com.assignment.shoponline.service;

import com.assignment.shoponline.entity.order.Order;
import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import com.assignment.shoponline.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    final OrderRepository orderRepository;

    public Order save(Order order) {
        return orderRepository.save(order);
    }

    //Xem danh sach don hang co phan trang
//    public findAllById(Long accountId)

    //Xem het cac don hang kem bo loc (danh cho admin)
    //dung pageable + page la phan trang duoc roi
    public Page<Order> search(Long id, Pageable pageable) {
        List<Order> orders = orderRepository.findAll();
        orders.stream().filter(order -> order.getId() == id).forEach(System.out::println);
        Page<Order> page = new PageImpl<>(orders);
        return page;
    }

//    public Order convertShoppingCartToOrderByAccountId(Long accountId) {
//
//    }
}
