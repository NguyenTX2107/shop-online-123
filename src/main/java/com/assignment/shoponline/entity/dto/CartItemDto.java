package com.assignment.shoponline.entity.dto;

import com.assignment.shoponline.entity.shoppingcart.CartItem;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDto {
    private long id;
    private String name;
    private BigDecimal unitPrice;
    private String images; // tập hợp các ảnh cách nhau bởi dấu ,
    private int quantity;

    public CartItemDto(CartItem cartItem) {
        if (null == cartItem || null == cartItem.getProduct()) {
            return;
        }
        this.id = cartItem.getProduct().getId();
        this.name = cartItem.getProduct().getName();
        this.images = cartItem.getProduct().getImage();
        this.unitPrice = cartItem.getUnitPrice();
        this.quantity = cartItem.getQuantity();
    }
}
