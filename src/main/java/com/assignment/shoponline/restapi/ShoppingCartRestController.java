package com.assignment.shoponline.restapi;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.dto.ShoppingCartDto;
import com.assignment.shoponline.entity.shoppingcart.CartItemId;
import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import com.assignment.shoponline.service.ProductService;
import com.assignment.shoponline.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/carts")
public class ShoppingCartRestController {
    final ShoppingCartService shoppingCartService;
    final ProductService productService;

    @GetMapping("{id}")
    public ResponseEntity<?> getShoppingCart(@PathVariable Long id)
    {
        try {
            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(id);
            if (null == shoppingCart.getItems()) {
                return ResponseEntity.ok().body("Shopping Cart is empty");
            }
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            return ResponseEntity.ok().body(shoppingCartDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while loading shopping cart");
        }
    }

    @PostMapping("{id}")
    public ResponseEntity<?> addCartItem(@PathVariable Long id, Authentication authentication) {
        try {
            Long accountId = Long.parseLong(authentication.getPrincipal().toString());
            Optional<Product> optionalProduct = productService.findById(id);
            if (!optionalProduct.isPresent()) {
                return ResponseEntity.badRequest().body("Product not found");
            }
            ShoppingCart shoppingCart = shoppingCartService.addItemToCart(optionalProduct.get(), accountId);
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            return ResponseEntity.ok().body(shoppingCartDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while adding product to shopping cart");
        }
    }

    @PutMapping
    public ResponseEntity<?> updateItemQuantity(
            @RequestParam(value = "cartItemId") CartItemId cartItemId,
            @RequestParam(value = "quantity") int quantity,
            Authentication authentication) {
        try {
            Long accountId = Long.parseLong(authentication.getPrincipal().toString());
            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
            shoppingCartService.updateQuantityToItem(cartItemId, quantity, shoppingCart);
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            return ResponseEntity.ok().body(shoppingCartDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while adding quality for cart item");
        }
    }

    @DeleteMapping({"{productId}"})
    public ResponseEntity<?> removeCartItem (@PathVariable Long productId, Authentication authentication){
        try {
            Long accountId = Long.parseLong(authentication.getPrincipal().toString());
            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
            if (null == shoppingCart) {
                return ResponseEntity.badRequest().body("Shopping Cart is empty");
            }
            boolean success = shoppingCartService.removeCartItem(shoppingCart, productId);
            if (!success) {
                return ResponseEntity.badRequest().body("Remove item fail");
            }
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            return ResponseEntity.ok().body(shoppingCartDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while removing item");
        }
    }

    @DeleteMapping
    public ResponseEntity<?> removeAllCartItems(Authentication authentication) {
        try {
            Long accountId = Long.parseLong(authentication.getPrincipal().toString());
            ShoppingCart shoppingCart = shoppingCartService.findByAccountId(accountId);
            if (null == shoppingCart) {
                System.out.println("Shopping cart is empty");
                return ResponseEntity.badRequest().body("Shopping Cart is empty");
            }
            shoppingCartService.removeAllCartItems(shoppingCart);
            ShoppingCartDto shoppingCartDto = new ShoppingCartDto(shoppingCart);
            return ResponseEntity.badRequest().body(shoppingCartDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error while removing all items");
        }
    }
}
