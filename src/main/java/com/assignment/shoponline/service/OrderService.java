package com.assignment.shoponline.service;

import com.assignment.shoponline.entity.order.Order;
import com.assignment.shoponline.entity.order.OrderDetail;
import com.assignment.shoponline.entity.shoppingcart.CartItem;
import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import com.assignment.shoponline.repository.OrderDetailRepository;
import com.assignment.shoponline.repository.OrderRepository;
import com.assignment.shoponline.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    final OrderRepository orderRepository;
    final OrderDetailRepository orderDetailRepository;
    final ShoppingCartRepository shoppingCartRepository;

    public Order convertShoppingCartToOrderByAccountId(Long accountId) {
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByAccountId(accountId);
        if (!optionalShoppingCart.isPresent()) {
            return null;
        }
        ShoppingCart shoppingCart = optionalShoppingCart.get();
        return new Order(shoppingCart);
    }

    public Order addOrderDetails(Order order, ShoppingCart shoppingCart) {
        Set<OrderDetail> details = order.getOrderDetails();
        if (null == details) {
            details = new HashSet<>();
        }
        Set<CartItem> items = shoppingCart.getItems();
        //Voi moi item trong cart thi tien hanh tao moi 1 orderdetail tuong ung va add luon vao order thong qua set<orderdetail>
        items.forEach(e -> {
            OrderDetail orderDetail = new OrderDetail(e);
        });
        return null;
    }
}
