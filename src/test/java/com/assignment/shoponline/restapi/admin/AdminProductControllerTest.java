package com.assignment.shoponline.restapi.admin;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.dto.ProductDto;
import com.assignment.shoponline.service.ProductService;
import com.assignment.shoponline.utils.Enums;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@SpringBootTest
class AdminProductControllerTest {
    @Autowired
    ProductService productService;

    @Test
    void create() {
        try {
            //Day la Dto truyen ve tu frontend
            Long adminId = 7L; //lay duoc tu token
            ProductDto productDto = new ProductDto();
            productDto.setName("Mouse");
            productDto.setCategory("Computer");
            productDto.setStatus(Enums.ProductStatus.ACTIVE);
            productDto.setImage("/wsdf");
            productDto.setDetail("Test case 13");
            productDto.setPrice(new BigDecimal(222000));
            try {
                String date = "06/06/2016"; //dữ liệu date truyền về từ frontend sẽ là String và đúng format dd/MM/yyyy
                productDto.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
            } catch (ParseException e) {
                System.out.println("Date nhap vao khong dung");
            }
            //
            if (!productDto.isDataValid()) {
                System.out.println("Data nhan vao khong dung");
            } else {
                productService.create(productDto, adminId);
                ResponseEntity.ok("Action Success");
                System.out.println("Tao thanh cong");
            }
        }catch (Exception e) {
            ResponseEntity.badRequest().body("Fail to create new product");
            System.out.println("Tao moi khong thanh cong");
        }
    }

    @Test
    void update() {
        try {
            //Day la Dto truyen ve tu frontend
            Long adminId = 4L;
            ProductDto productDto = new ProductDto();
            productDto.setId(13L);
            productDto.setName("MacBook Pro 2021");
            productDto.setCategory("Laptop");
            productDto.setStatus(Enums.ProductStatus.DEACTIVATE);
            productDto.setImage("/sdf/Aqwreerf");
            productDto.setDetail("Test case 13");
            productDto.setPrice(new BigDecimal(69999999));
            try {
                String date = "12/11/2022"; //dữ liệu date truyền về từ frontend sẽ là String và đúng format dd/MM/yyyy
                productDto.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(date));
            } catch (ParseException e) {
                System.out.println("Date nhap vao khong dung");
            }
            //
            if (!productDto.isDataValid()) {
                System.out.println("Data nhan vao khong dung");
            } else {
                Product existProduct = new Product();
                productService.update(productDto, existProduct, adminId);
                System.out.println("Update thanh cong");
            }
        } catch (Exception e) {
            System.out.println("Update khong thanh cong");
        }
    }

    @Test
    void delete() {
        Long id = 5L;
        Long adminId = 22L;
        try {
            Optional<Product> optionalProduct = productService.findById(id);
            if (!optionalProduct.isPresent()) {
                System.out.println("Khong tim thay san pham");
            }
            Product product = optionalProduct.get();
            productService.delete(product, adminId);
            //Kiem tra lai status khi da xoa
            Optional<Product> checkResult = productService.findById(id);
            if (checkResult.isPresent()) {
                System.out.println("Trang thai cua san pham: "+checkResult.get().getStatus());
                System.out.println("Xoa boi: "+checkResult.get().getDeletedBy());
                System.out.println("Tai: "+checkResult.get().getDeletedAt());
            }
        } catch (Exception e) {
            System.out.println("Xoa that bai");
        }
    }
}