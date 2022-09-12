package com.assignment.shoponline.restapi.admin;

import com.assignment.shoponline.entity.Account;
import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.dto.ProductDto;
import com.assignment.shoponline.service.AccountService;
import com.assignment.shoponline.service.ProductService;
import com.assignment.shoponline.utils.Enums;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/admin/v1/products")
public class AdminProductController {
    final ProductService productService;
    final AccountService accountService;

    @GetMapping("{id}")
    public ResponseEntity<?> getDetail(@PathVariable Long id) {
        try {
            Optional<Product> optionalProduct = productService.findById(id);
            if (!optionalProduct.isPresent()) {
                return ResponseEntity.badRequest().body("Product not found");
            }
            ProductDto productDto = new ProductDto(optionalProduct.get());
            return ResponseEntity.status(HttpStatus.FOUND).body(productDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Can not get product detail");
        }
    }

    @GetMapping
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

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDto productDto, Authentication authentication) {
        try{
            authentication = SecurityContextHolder.getContext().getAuthentication(); //test: authen da duoc luu lai trong context
            Account account = accountService.findByUsername(authentication.getName());
            productService.create(productDto, account.getId());
            return ResponseEntity.ok("Create Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error when creating new product");
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProductDto productDto, Authentication principal) {
        try {
            Long adminId = Long.parseLong(principal.getName());
            if (!productDto.isDataValid()) {
                return ResponseEntity.badRequest().body("Input product information is invalid");
            } else {
                Product existProduct = new Product();
                productService.update(productDto, existProduct, adminId);
                return ResponseEntity.ok("Update Success");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Fail to update product information");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        try {
            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
            long adminId = Long.parseLong(principal.getName());
            Optional<Product> optionalFood = productService.findById(id);
            if (!optionalFood.isPresent()) {
                return ResponseEntity.badRequest().body("Food not found");
            }
            Product existFood = optionalFood.get();
            productService.delete(existFood, adminId);
            return ResponseEntity.ok("Delete product successfully");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Delete failed");
        }
    }

}
