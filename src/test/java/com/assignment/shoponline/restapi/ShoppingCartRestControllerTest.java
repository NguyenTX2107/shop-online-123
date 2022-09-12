package com.assignment.shoponline.restapi;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.dto.ShoppingCartDto;
import com.assignment.shoponline.entity.shoppingcart.CartItemId;
import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import com.assignment.shoponline.repository.CartItemRepository;
import com.assignment.shoponline.service.ProductService;
import com.assignment.shoponline.service.ShoppingCartService;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@SpringBootTest
@ToString
class ShoppingCartRestControllerTest {

    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    ProductService productService;
    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    void getShoppingCart() { //ngon
        Long accountId = 3L;
        ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
        if (null == shoppingCart.getItems()) {
            ResponseEntity.ok().body("Empty Shopping Cart");
            System.out.println("Empty Shopping Cart");
        }
        else {
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            ResponseEntity.ok().body(shoppingCartDto);
            System.out.println(shoppingCartDto.getItems().size());
            System.out.println(shoppingCartDto.getTotalPrice());
            System.out.println(shoppingCartDto.getAccountId());
            System.out.println(shoppingCartDto);
        }
        System.out.println(shoppingCart.getAccountId());
    }

    @Test
    void addCartItem() { //ngon
        Long accountId = 2L;
        Long productId = 7L; //productId
        try {
            Optional<Product> optionalProduct = productService.findById(productId);
            if (!optionalProduct.isPresent()) {
                ResponseEntity.badRequest().body("Product not found");
                System.out.println("Product not found");
                return;
            }
            ShoppingCart shoppingCart = shoppingCartService.addItemToCart(optionalProduct.get(), accountId);
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            ResponseEntity.ok().body(shoppingCartDto);
            System.out.println(shoppingCartDto.getTotalPrice());
            System.out.println(shoppingCartDto.getItems().size());
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Error while adding product to shopping cart");
            System.out.println("Error while adding product to shopping cart");
        }
    }

//    @Test
//    void changeItemQuantity() { //ngon
//        CartItemId cartItemId = new CartItemId(3L, 4L);
//        int quantity = 1;
//        try {
//            ShoppingCart shoppingCart = shoppingCartService.updateQuantityToItem(cartItemId, quantity, s);
//            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
//            ResponseEntity.ok().body(shoppingCartDto);
//            System.out.println(shoppingCartDto.getTotalPrice());
//            System.out.println(shoppingCartDto);
//        } catch (Exception e) {
//            ResponseEntity.badRequest().body("Error while adding quality for cart item");
//        }
//    }

    @Test
    void removeCartItem() { //ngon
        Long accountId = 2L;
        Long productId = 11L;
        try {
            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
            if (null == shoppingCart) {
                System.out.println("Shopping Cart is empty");
                return;
            }
            System.out.println(shoppingCart);
            if (!shoppingCartService.removeCartItem(shoppingCart, productId)) {
                System.out.println("Delete fail");
                return;
            };
            System.out.println(shoppingCart);
            System.out.println("Delete Cart Item successfully");
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            System.out.println(shoppingCartDto);
            ResponseEntity.ok().body(shoppingCartDto);
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Error while deleting cart item");
            System.out.println("Error while deleting cart item");
        }
    }

    @Test
    //goi khi nhan nut xoa het cart item hoac khi submit thanh order
    void removeAllCartItems() { //ngon
        Long accountId = 2L;
        try {
            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
            if (null == shoppingCart) {
                System.out.println("Shopping cart is empty");
                return;
            }
            System.out.println(shoppingCart);
            shoppingCartService.removeAllCartItems(shoppingCart);
            System.out.println("Remove all successfully");
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            System.out.println(shoppingCartDto);
        } catch (Exception e) {
            System.out.println("Error while removing all cart items");
        }
    }
}