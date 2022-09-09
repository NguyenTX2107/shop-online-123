package com.assignment.shoponline.restapi.admin;

import com.assignment.shoponline.entity.order.Order;
import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import com.assignment.shoponline.service.OrderService;
import com.assignment.shoponline.service.ShoppingCartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AdminOrderControllerTest {

    @Autowired
    OrderService orderService;
    @Autowired
    ShoppingCartService shoppingCartService;

    @Test
    public void convertShoppingCartToOrderByAccountId() {
        Long accountId = 2L;
        ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
        Order order = new Order(shoppingCart);
        orderService.save(order);
        System.out.println(order);
    }
}