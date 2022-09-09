package com.assignment.shoponline.entity.order;

import com.assignment.shoponline.entity.basic.BaseEntity;
import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import com.assignment.shoponline.utils.Enums;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "order_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "10000"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    private Long accountId;
    private BigDecimal discount;
    private BigDecimal totalPrice;
    @Enumerated(EnumType.ORDINAL)
    private Enums.OrderStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private ShoppingCart cart;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<OrderDetail> orderDetails;

    public Order(ShoppingCart shoppingCart) {
        this.cart = shoppingCart;
        this.accountId = shoppingCart.getAccountId();
        this.totalPrice = shoppingCart.getTotalPrice();
        this.discount = BigDecimal.ZERO;
    }

    public void calculateTotalPrice(List<OrderDetail> details) {
        details.forEach(e -> {
            this.totalPrice = this.totalPrice.add(e.getUnitPrice().multiply(new BigDecimal(e.getQuantity())));
        });
    }

    public void calculateTotalPrice() {
        this.totalPrice = BigDecimal.valueOf(0);
        if (this.orderDetails == null || this.orderDetails.size() == 0) {
            return;
        }
        this.orderDetails.forEach(e -> {
            this.totalPrice = this.totalPrice.add(e.getUnitPrice().multiply(new BigDecimal(e.getQuantity())));
        });
    }
}
