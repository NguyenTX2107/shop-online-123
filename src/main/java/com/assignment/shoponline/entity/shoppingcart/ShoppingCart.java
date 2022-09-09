package com.assignment.shoponline.entity.shoppingcart;

import com.assignment.shoponline.entity.basic.BaseEntity;
import com.assignment.shoponline.entity.order.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accountId;
    private BigDecimal totalPrice;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private Set<CartItem> items;

    public CartItem getItemByProductId(Long productId) {
        if (null == this.items || this.items.size() == 0) {
            return null;
        }
        for (CartItem item :
                items) {
            if (item.getId().getProductId() == productId) {
                return item;
            }
        }
        return null;
    }

    public void updateTotalPrice() {
        this.totalPrice = new BigDecimal(0);
        if (null == this.items) {
            return;
        }
        for (CartItem cartItem :
                items) {
            BigDecimal quantityInBigDecimal = new BigDecimal(cartItem.getQuantity());
            this.totalPrice = this.totalPrice.add(cartItem.getUnitPrice().multiply(quantityInBigDecimal));
        }
    }
}