//package com.assignment.shoponline.controller.admin;
//
//import com.assignment.shoponline.entity.Product;
//import com.assignment.shoponline.utils.Enums;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import com.assignment.shoponline.entity.dto.ProductDto;
//import com.assignment.shoponline.service.ProductService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//import java.util.Optional;
//
//@CrossOrigin("*")
//@Slf4j
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("admin/products")
//public class ProductAdminController {
//    final ProductService productService;
//
//    final AuthenticationManager authenticationManager;
//
//     @GetMapping("{id}")
//     public String getDetail(@PathVariable Long id, Model model) {
//         try {
//             Optional<Product> optionalProduct = productService.findById(id);
//             if (!optionalProduct.isPresent()) {
//                 model.addAttribute("message_error", "Product not found");
//                 return "redirect:/admin/products";
//             }
//             ProductDto productDto = new ProductDto(optionalProduct.get());
//             model.addAttribute("productDto", productDto);
//             return "admin";
//         } catch (Exception e) {
//             model.addAttribute("message_error", "Error while get product detail");
//             return "admin";
//         }
//     }
//
//    @GetMapping //dung cho main page, loc luon
//    public String search(
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            @RequestParam(value = "limit", defaultValue = "4") int limit,
//            @RequestParam(value = "name", defaultValue = "") String name,
//            @RequestParam(value = "priceFrom", defaultValue = "0") int priceFrom,
//            @RequestParam(value = "priceTo", defaultValue = "1000000000") int priceTo,
//            @RequestParam(value = "status", defaultValue = "") Enums.ProductStatus status,
//            @RequestParam(value = "category", defaultValue = "") String category,
//            Model model) {
//        try {
//            Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("createdAt").descending());
//            Page<Product> products = productService.search(name, priceFrom, priceTo, status, category, pageable);
//            List<Product> listProducts = products.getContent();
//            if (listProducts.isEmpty()) {
//                model.addAttribute("productMessage", "Empty");
//                return "admin";
//            }
//            //truyen ve index page bang model
//            model.addAttribute("totalPages", products.getTotalPages());
//            model.addAttribute("page", page);
//            model.addAttribute("products", listProducts);
//            return "admin";
//        } catch (Exception e) {
//            model.addAttribute("productMessage", "Error while getting list products");
//            return "admin";
//        }
//    }
//
//    @GetMapping("form")
//    public String getCreateProductForm(Model model) {
//        try {
//            ProductDto productDto = new ProductDto();
//            model.addAttribute("productDto", productDto);
//            return "product";
//        } catch(Exception e) {
//            log.error("Can not get product form");
//            return "redirect:/admin/products";
//        }
//    }
//
//    @PostMapping
//    public String create(@ModelAttribute("productDto") ProductDto productDto, Authentication authentication) {
//        try{
////            authentication = SecurityContextHolder.getContext().getAuthentication(); //lay token cua user hien tai ra
////            System.out.println(authentication.getName());
////            Long adminId = Long.parseLong(authentication.getName());
//            Long adminId=4L;
//            productService.create(productDto, adminId);
//            log.info("Create new product successfully");
//            return "redirect:/"; //tra ve trang danh sach san pham, can thay bang link den trang san pham cua admin
//        } catch (Exception e) {
//            log.error("Error while creating new product");
//            return "product";
//        }
//    }
//
////     @PutMapping("{id}")
////     public String update(@RequestBody ProductDto productDto, Authentication principal) {
////         try {
////             Long adminId = Long.parseLong(principal.getName());
////             if (!productDto.isDataValid()) {
////                 return ResponseEntity.badRequest().body("Input product information is invalid");
////             } else {
////                 Product existProduct = new Product();
////                 productService.update(productDto, existProduct, adminId);
////                 return ResponseEntity.ok("Update Success");
////             }
////         } catch (Exception e) {
////             return ResponseEntity.badRequest().body("Fail to update product information");
////         }
////     }
//
//    // @DeleteMapping("{id}")
//    // public ResponseEntity<?> delete(@PathVariable Long id){
//    //     try {
//    //         Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//    //         long adminId = Long.parseLong(principal.getName());
//    //         Optional<Product> optionalFood = productService.findById(id);
//    //         if (!optionalFood.isPresent()) {
//    //             return ResponseEntity.badRequest().body("Food not found");
//    //         }
//    //         Product existFood = optionalFood.get();
//    //         productService.delete(existFood, adminId);
//    //         return ResponseEntity.ok("Delete product successfully");
//
//    //     } catch (Exception e) {
//    //         return ResponseEntity.badRequest().body("Delete failed");
//    //     }
//    // }
//}
