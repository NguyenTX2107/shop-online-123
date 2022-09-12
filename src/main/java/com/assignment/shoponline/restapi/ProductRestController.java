package com.assignment.shoponline.restapi;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.dto.ProductDto;
import com.assignment.shoponline.service.ProductService;
import com.assignment.shoponline.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/products")
public class ProductRestController {
    final ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        try {
            Optional<Product> optionalProduct = productService.findById(id);
            if (!optionalProduct.isPresent()) {
                return ResponseEntity.badRequest().body("Product not found");
            }
            ProductDto productDto = new ProductDto(optionalProduct.get());
            return ResponseEntity.status(HttpStatus.OK.value()).body(productDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Can not get product detail");
        }
    }

    @GetMapping //dung cho main page, loc luon
    public ResponseEntity<?> search(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "limit", defaultValue = "5") int limit,
                                    @RequestParam(value = "name", defaultValue = "") String name,
                                    @RequestParam(value = "priceFrom", defaultValue = "0") int priceFrom,
                                    @RequestParam(value = "priceTo", defaultValue = "1000000000") int priceTo,
                                    @RequestParam(value = "status", defaultValue = "") Enums.ProductStatus status,
                                    @RequestParam(value = "category", defaultValue = "") String category) {
        try {
            Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
            Page<Product> products = productService.search(name, priceFrom, priceTo, status, category, pageable);

            return ResponseEntity.status(HttpStatus.OK.value()).body(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Action fails.");
        }
    }
}
