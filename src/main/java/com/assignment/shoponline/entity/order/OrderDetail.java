package com.assignment.shoponline.entity.order;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.shoppingcart.CartItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "order_details")
public class OrderDetail  {
    @EmbeddedId
    private OrderDetailId id;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    @JsonBackReference
    private Order order;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    @JsonManagedReference
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderDetail(CartItem cartItem) {
        this.product = cartItem.getProduct();
        this.quantity = cartItem.getQuantity();
        this.unitPrice = cartItem.getUnitPrice();
    }
}