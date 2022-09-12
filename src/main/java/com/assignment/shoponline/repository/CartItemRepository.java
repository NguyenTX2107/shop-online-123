package com.assignment.shoponline.repository;

import com.assignment.shoponline.entity.shoppingcart.CartItem;
import com.assignment.shoponline.entity.shoppingcart.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    Optional<CartItem> findById(CartItemId cartItemId);

    void deleteById(CartItemId cartItemId);
}
