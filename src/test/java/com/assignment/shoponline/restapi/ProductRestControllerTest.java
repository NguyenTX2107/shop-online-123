package com.assignment.shoponline.restapi;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.dto.ProductDto;
import com.assignment.shoponline.service.ProductService;
import com.assignment.shoponline.utils.Enums;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@SpringBootTest
@ToString
class ProductRestControllerTest {

    @Autowired
    private ProductService productService;

    @Test
    public void getDetail() { //ngon
        Long id = 4L;
        try {
            Optional<Product> optionalProduct = productService.findById(id);
            if (!optionalProduct.isPresent()) {
               ResponseEntity.badRequest().body("Product not found");
               System.out.println("Du lieu trong");
            }
            ResponseEntity.ok(200);
            System.out.println(optionalProduct.get().getName());
            System.out.println("Truyen nhan ok");
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Can not get product detail");
        }
    }

    @Test
    void search() { //ngon
        try {
            //Cac gia tri loc nhan ve tu front end
            int page = 1;
            int limit = 3;
            String name = "";
            int priceFrom = 0;
            int priceTo = 100000000;
            Enums.ProductStatus status = null;
            String category = "Food";
            //
            Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
            Page<Product> products = productService.search(name, priceFrom, priceTo, status, category, pageable);
            products.map(ProductDto::new);
            System.out.println("Tim kiem thanh cong");
            System.out.println(products.getTotalElements());
            System.out.println(products.getTotalPages());
        } catch (Exception e) {
            System.out.println("Tim kiem that bai");
        }
    }
}