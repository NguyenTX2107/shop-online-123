package com.assignment.shoponline.restapi.admin;

import com.assignment.shoponline.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/v1/orders")
public class AdminOrderController {
    final OrderService orderService;

//    @GetMapping
//    public ResponseEntity<?> search(
//                                @RequestParam(value = "id") Long id
//    ) {
//        try {
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Error");
//        }
//    }
}
