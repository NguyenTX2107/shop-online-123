package com.assignment.shoponline.repository;

import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByAccountId(Long accountId);

    Optional<ShoppingCart> findById(Long id);
}
