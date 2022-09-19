//package com.assignment.shoponline.controller;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.assignment.shoponline.entity.Product;
//import com.assignment.shoponline.entity.dto.ProductDto;
//import com.assignment.shoponline.service.ProductService;
//import com.assignment.shoponline.utils.Enums;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@CrossOrigin("*")
//@Slf4j
//@RequiredArgsConstructor
//@Controller
//@RequestMapping
//public class ProductController {
//    final ProductService productService;
//
//    @GetMapping("{id}")
//    public String getDetail(@PathVariable Long id, Model model) {
//        try {
//            Optional<Product> optionalProduct = productService.findById(id);
//            if (!optionalProduct.isPresent()) {
//                log.info("Product not found");
//                model.addAttribute("productMessage", "Product not found");
//                return "index";
//            }
//            ProductDto productDto = new ProductDto(optionalProduct.get());
//            log.info("Found product with id "+productDto.getId());
//            model.addAttribute(productDto); //truyen productDto ve frontend
//            return "index";
//        } catch (Exception e) {
//            log.error("Error while getting product information");
//            model.addAttribute("productMessage", "Error while geting detail");
//            return "index";
//        }
//    }
//
//    @GetMapping //dung cho main page, loc luon
//    public String search(
//                        @RequestParam(value = "page", defaultValue = "1") int page,
//                        @RequestParam(value = "limit", defaultValue = "4") int limit,
//                        @RequestParam(value = "name", defaultValue = "") String name,
//                        @RequestParam(value = "priceFrom", defaultValue = "0") int priceFrom,
//                        @RequestParam(value = "priceTo", defaultValue = "1000000000") int priceTo,
//                        @RequestParam(value = "status", defaultValue = "") Enums.ProductStatus status,
//                        @RequestParam(value = "category", defaultValue = "") String category,
//                        Model model) {
//        try {
//            Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
//            Page<Product> products = productService.search(name, priceFrom, priceTo, status, category, pageable);
//            List<Product> listProducts = products.getContent();
//            if (listProducts.isEmpty()) {
//                model.addAttribute("productMessage", "Empty");
//                return "index";
//            }
//            //truyen ve index page bang model
//            model.addAttribute("totalPages", products.getTotalPages());
//            model.addAttribute("page", page);
//            model.addAttribute("products", listProducts);
//            return "index";
//        } catch (Exception e) {
//            model.addAttribute("productMessage", "Error while getting list products");
//            return "index";
//        }
//    }
//}
