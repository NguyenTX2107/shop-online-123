package com.assignment.shoponline.entity.dto;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.utils.Enums;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private Long id;
    private String name; //ten san pham
    private String category;
    private String detail; //chi tiet
    private String image; //anh
    private BigDecimal price; //don gia
    @CreationTimestamp
    private Date date;
    private Enums.ProductStatus status;

    public ProductDto(Product product) { //copy nhung thuoc tinh can truyen di tu Product vao ProductDTO
        BeanUtils.copyProperties(product, this);
    }

    /**
     * Kiểm tra xem dữ liệu cần truyền hay nhận có ok không
     * @return true if data is valid
     */
    public boolean isDataValid() {
        if (null == this.getName() || this.getName().equals("")) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product Name is not null");
            return false;
        }
        if (null == this.getCategory() || this.getCategory().equals("")) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category is not null");
            return false;
        }
        if (null == this.getImage() || this.getImage().equals("")) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image is not null");
            return false;
        }
        if (null == this.getPrice()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Price is not null");
            return false;
        }
        if (null == this.getDetail()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Detail is not null");
            return false;
        }
        if (null == this.getStatus()) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Status is not null");
            return false;
        }
//        if (null == this.getDate()) {
//            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date is not null");
//            return false;
//        }
        return true;
    }
}
