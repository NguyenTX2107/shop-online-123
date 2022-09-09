package com.assignment.shoponline.entity.dto;

import com.assignment.shoponline.entity.Account;
import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ShoppingCartDto {
    private Long id;
    private Long accountId;
    private BigDecimal totalPrice;
    private Set<CartItemDto> items;

    public ShoppingCartDto(ShoppingCart cart) {
        this.id = cart.getId();
        this.accountId = cart.getAccountId();
        this.totalPrice = cart.getTotalPrice();
        this.items = cart.getItems().stream().map(CartItemDto::new).collect(Collectors.toSet());
    }
}
