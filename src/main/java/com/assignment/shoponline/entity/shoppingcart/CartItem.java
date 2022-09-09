package com.assignment.shoponline.entity.shoppingcart;

import com.assignment.shoponline.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "cart_items")
public class CartItem {
    @EmbeddedId
    private CartItemId id;
    private String productName; // dùng để đỡ phải query lại trong database.
    private String productImage;
    private int quantity = 1; //default
    private BigDecimal unitPrice; // giá tại thời điểm mua.
    @ManyToOne
    @MapsId("shoppingCartId")
    @JoinColumn(name = "shopping_cart_id", insertable = false, updatable = false)
    @JsonManagedReference
    private ShoppingCart cart;
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    @JsonManagedReference
    private Product product;

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }
}
