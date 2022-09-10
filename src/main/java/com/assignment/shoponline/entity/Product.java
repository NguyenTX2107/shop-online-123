package com.assignment.shoponline.entity;

import com.assignment.shoponline.entity.basic.BaseEntity;
import com.assignment.shoponline.utils.Enums;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; //ten san pham
    private String category; //nhan (nhap thu cong :V)
    @Lob
    private String detail; //chi tiet
    private String image; //anh
    private BigDecimal price; //don gia
    @DateTimeFormat
    private Date date; //ngay san xuat
    @Enumerated(EnumType.ORDINAL)
    private Enums.ProductStatus status;

}
