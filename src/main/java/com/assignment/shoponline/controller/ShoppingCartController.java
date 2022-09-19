//package com.assignment.shoponline.controller;
//
//import com.assignment.shoponline.entity.Product;
//import com.assignment.shoponline.entity.dto.ShoppingCartDto;
//import com.assignment.shoponline.entity.shoppingcart.CartItemId;
//import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
//import com.assignment.shoponline.service.ProductService;
//import com.assignment.shoponline.service.ShoppingCartService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//@CrossOrigin("*")
//@Slf4j
//@RequiredArgsConstructor
//@Controller
//@RequestMapping("users/shopping-cart")
//public class ShoppingCartController {
//    final ShoppingCartService shoppingCartService;
//    final ProductService productService;
//
//    @GetMapping
//    public String test() {
//        return "index";
//    }

//    @GetMapping("{id}")
//    public String getShoppingCart(@PathVariable Long id)
//    {
//        try {
//            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(id);
//            if (null == shoppingCart.getItems()) {
//                log.error("Shopping Cart is empty");
//            }
//            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
//            return ResponseEntity.ok().body(shoppingCartDto);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error while loading shopping cart");
//        }
//    }

//    @PostMapping("{id}")
//    public ResponseEntity<?> addCartItem(@PathVariable Long id, @RequestParam(value = "accountId") Long accountId) {
//        try {
//            Optional<Product> optionalProduct = productService.findById(id);
//            if (!optionalProduct.isPresent()) {
//                return ResponseEntity.badRequest().body("Product not found");
//            }
//            ShoppingCart shoppingCart = shoppingCartService.addItemToCart(optionalProduct.get(), accountId);
//            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
//            return ResponseEntity.ok().body(shoppingCartDto);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error while adding product to shopping cart");
//        }
//    }
//
//    @PutMapping
//    public ResponseEntity<?> updateItemQuantity(
//            @RequestParam(value = "cartItemId") CartItemId cartItemId,
//            @RequestParam(value = "quantity") int quantity) {
//        try {
//            ShoppingCart shoppingCart = shoppingCartService.updateQuantityToItem(cartItemId, quantity);
//            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
//            return ResponseEntity.ok().body(shoppingCartDto);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error while adding quality for cart item");
//        }
//    }
//
//    @DeleteMapping
//    public ResponseEntity<?> removeCartItem (
//            @RequestParam(value = "accountId") Long accountId,
//            @RequestParam(value = "productId") Long productId) {
//        try {
//            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
//            if (null == shoppingCart) {
//                return ResponseEntity.badRequest().body("Shopping Cart is empty");
//            }
//            boolean success = shoppingCartService.removeCartItem(shoppingCart, productId);
//            if (!success) {
//                return ResponseEntity.badRequest().body("Remove item fail");
//            }
//            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
//            return ResponseEntity.ok().body(shoppingCartDto);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error while removing item");
//        }
//    }
//
//    @DeleteMapping("{id}")
//    public ResponseEntity<?> removeAllCartItems(@PathVariable Long accountId) {
//        try {
//            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
//            if (null == shoppingCart) {
//                System.out.println("Shopping cart is empty");
//                return ResponseEntity.badRequest().body("Shopping Cart is empty");
//            }
//            shoppingCartService.removeAllCartItems(shoppingCart);
//            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
//            return ResponseEntity.badRequest().body(shoppingCartDto);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error while removing all items");
//        }
//    }
//}
