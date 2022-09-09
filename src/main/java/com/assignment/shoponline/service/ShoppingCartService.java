package com.assignment.shoponline.service;

import com.assignment.shoponline.entity.Product;
import com.assignment.shoponline.entity.shoppingcart.CartItem;
import com.assignment.shoponline.entity.shoppingcart.CartItemId;
import com.assignment.shoponline.entity.shoppingcart.ShoppingCart;
import com.assignment.shoponline.repository.CartItemRepository;
import com.assignment.shoponline.repository.ShoppingCartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {
    final ShoppingCartRepository shoppingCartRepository;
    final CartItemRepository cartItemRepository;

    /**
     * 1. Lấy thông tin cart theo id người dùng từ database ra.
     * 2. Check sản phẩm tồn tại trong cart chưa.
     * 3 Nếu chưa, tạo mới, thêm và cart.
     * 4. Save lại vào database.
     * Phần thêm số lượng item sẽ dùng method khác ở dưới :v
     * Mặc định khi add cart thì quantity của nó là 1 (xem CartItem Entity)
     */
    @Transactional
    public ShoppingCart addItemToCart(Product product, Long accountId) {
        // tạo giỏ hàng mặc định.
        ShoppingCart defaultShoppingCart = null;
        // tìm giỏ hàng theo id người dùng xem có chưa. Nhớ là một tài khoản chỉ có một shopping cart.
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByAccountId(accountId);
        if (optionalShoppingCart.isPresent()) {
            // trường hợp tồn tại.
            defaultShoppingCart = optionalShoppingCart.get();
        }
        // trường hợp không có thì tạo mới với tài khoản người dùng.
        if (null == defaultShoppingCart) {
            defaultShoppingCart = ShoppingCart.builder()
                    .accountId(accountId)
                    .build();
            // save để lấy id, lưu ý đang dùng transactional.
            defaultShoppingCart = shoppingCartRepository.save(defaultShoppingCart);
        }
        // lấy thông tin sản phẩm trong giỏ hàng nếu giỏ hàng đã tồn tại.
        Set<CartItem> items = defaultShoppingCart.getItems();
        if (items == null) {
            // nếu ko có thì tạo mới.
            items = new HashSet<>();
        }
        // kiểm tra sự tồn tại của sản phẩm trong giỏ hàng.
        CartItem cartItem = defaultShoppingCart.getItemByProductId(product.getId());
        // tạo cart item.
        if (null == cartItem) {
            // không có thì tạo mới.
            cartItem = new CartItem();
            CartItemId cartItemId = new CartItemId(defaultShoppingCart.getId(), product.getId());
            cartItem.setId(cartItemId);
            cartItem.setProduct(product);
            cartItem.setProductName(product.getName());
            cartItem.setProductImage(product.getImage());
            cartItem.setUnitPrice(product.getPrice());
            cartItem.setCart(defaultShoppingCart);
            items.add(cartItem);
        }
        // set lại danh sách sản phẩm vào giỏ hàng.
        defaultShoppingCart.setItems(items);
        // update lại tổng giá
        defaultShoppingCart.updateTotalPrice();
        return shoppingCartRepository.save(defaultShoppingCart);
    }

    @Transactional
    public ShoppingCart findByAccountId(Long accountId) {
        // tạo giỏ hàng mặc định.
        ShoppingCart defaultShoppingCart = null;
        // tìm giỏ hàng theo id người dùng xem có chưa. Nhớ là một tài khoản chỉ có một shopping cart.
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findByAccountId(accountId);
        if (optionalShoppingCart.isPresent()) {
            // trường hợp tồn tại.
            defaultShoppingCart = optionalShoppingCart.get();
        }
        // trường hợp không có thì tạo mới với tài khoản người dùng.
        if (null == defaultShoppingCart) {
            defaultShoppingCart = ShoppingCart.builder()
                    .accountId(accountId)
                    .build();
        }
        // save lại vào database không mất
        shoppingCartRepository.save(defaultShoppingCart);
        return defaultShoppingCart;
    }

    @Transactional
    public ShoppingCart updateQuantityToItem(CartItemId cartItemId, int quantity) throws Exception{
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (!cartItemOptional.isPresent()) {
            throw new Exception("Cart Item not found");
        }
        CartItem cartItem = cartItemOptional.get();
        cartItem.changeQuantity(quantity);
        //luu lai quantity cua cart item vao database
        cartItemRepository.save(cartItem);
        //tìm lại shopping cart trong database để update lại total price
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findById(cartItemId.getShoppingCartId());
        if (!shoppingCart.isPresent()) {
            throw new Exception("Shopping Cart not found");
        }
        shoppingCart.get().updateTotalPrice();
        return shoppingCart.get();
    }

    /**
     *
     * @param shoppingCart
     * @param productId
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean removeCartItem(ShoppingCart shoppingCart, Long productId) {
        // kiểm tra sự tồn tại của sản phẩm trong giỏ hàng.
        CartItem cartItem = shoppingCart.getItemByProductId(productId);
        if (null == cartItem) {
            System.out.println("Cart Item found");
            return false;
        }
        shoppingCart.getItems().remove(cartItem);
        shoppingCart.updateTotalPrice();
        shoppingCartRepository.save(shoppingCart);
        return true;
    }

    /**
     * Gọi hàm này khi nhấn nút xóa tất hoặc đã submit đơn hàng, xóa tất cả các cart item và đặt lại total price
     * @param shoppingCart
     * @return true if success
     */
    public boolean removeAllCartItems(ShoppingCart shoppingCart) {
        shoppingCart.getItems().clear();
        shoppingCart.updateTotalPrice();
        shoppingCartRepository.save(shoppingCart);
        return true;
    }
}
